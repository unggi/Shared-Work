package com.nomura.unity.takara.server.tvs;

import com.google.common.base.Strings;
import com.nomura.fi.takara.models.account.*;
import com.nomura.fi.takara.models.account.defaulting.ProductCounterpartyValidationResult;
import com.nomura.fi.takara.models.account.defaulting.ValidationErrorCode;
import com.nomura.fi.takara.models.repository.ModelRepository;
import com.nomura.fi.takara.tvs.model.Trade;
import com.nomura.fi.takara.util.DateUtils;
import org.joda.time.DateMidnight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by misherma on 8/1/2015.
 */
public class NFPSCounterpartyValidationRule extends AbstractValidationRule {
    public static final String NFPS = "NFPS";
    public static final String NIP = "NIP";

    private static final Logger logger = LoggerFactory.getLogger(NFPSCounterpartyValidationRule.class);

    public NFPSCounterpartyValidationRule(String name, ModelRepository repository) {
        super(name, repository);
    }

    @Override
    public boolean isApplicable(Trade trade, ValidationContext validationContext) {
        Account account = trade.getCounterpartyOrThirdParty().getAccount();
        if (trade.getTradeDate() == null) {
            return false;
        }
        if (account == null || !(account instanceof TradeAccount) ) {
            return false;
        }

        if (Strings.isNullOrEmpty(trade.getProductType())) {
            return false;
        }
        return true;
    }

    private static final String PRECONDITIONS_DESCRIPTION = "Rule is applied if the counterparty or third party are specified and are a TradeAccount (not Book), and the ISDA productType is specified";

    private static final String DESCRIPTION = "Checks that the specified productType can be trade with the specified counterparty";

    @Override
    p@ublic String getPreconditionsDescription() {
        return PRECONDITIONS_DESCRIPTION;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public boolean validate(Trade trade, ValidationContext validationContext) {
        TradeAccount account = (TradeAccount) trade.getCounterpartyOrThirdParty().getAccount();

        ProductCounterpartyValidationResult result = isValid(trade.getTradeDate(), (TradeAccount) account, trade.getProductType());
        if (!result.isValid()) {
            result.setSuggestedAccount(findSuggestedAccount(trade.getTradeDate(), (TradeAccount) account, trade.getProductType()));
        } else {
            checkForSuggestedAccountAsWarning(result, trade.getTradeDate(), (TradeAccount) account, trade.getProductType());
        }

        validationContext.mergeResult(result);

        return result.isValid();
    }

    private String findOtherLegalEntity(TradeAccount account) {
        //TODO: This must be extensible to a more general case beyond NFPS/NIP
        if (NFPS.equals(account.getNomuraCompany()) ) {
            return NIP;
        }
        return NFPS;
    }


    protected void checkForSuggestedAccountAsWarning(ProductCounterpartyValidationResult result, DateMidnight tradeDate, TradeAccount account, String productType) {
        if (!"NIP".equals(account.getNomuraCompany()) ) {
            return;
        }

        //If the particular account is being marked to skip validation then do not find account suggestion for it
        if ("true".equalsIgnoreCase(account.getAttributeValue(SKIP_VALIDATION_ATTRIBUTE)) ) {
            return;
        }

        Organisation owningOrg = account.getOwningOrg();
        if (owningOrg != null) {
            List<String> clientTypes = owningOrg.getClassificationValues(Organisation.OrganisationClassification.CLIENT_TYPE_CLTY);
            if (clientTypes != null && clientTypes.contains("IEAC")) {
                logger.info("Counterparty:" + account.getShortName() + " is an Inter entity affiliate cpty. Skipping from validation");
                return;
            }
        }

        TradeAccount suggestedAccount = findSuggestedAccount(tradeDate, account, productType);
        if (suggestedAccount != null) {
            result.setSuggestedAccount(suggestedAccount);
            result.addMessage(ValidationErrorCode.CAN_BOOK_THROUGH_NFPS, String.format("There is a valid NFPS account for this product: %s. Please consider using it for this trade.",
                    buildAccountNameForErrorMessage(suggestedAccount)));
        }
    }

    private String buildAccountNameForErrorMessage(TradeAccount account) {
        String totoroName = account.getAliasValue(TradeAccount.AccountAlias.TOTORO_NAME);
        if (totoroName == null) {
            return String.format("%s (RDM %s)", account.getLongName(), account.getExternalId());
        }
        return String.format("%s (%s, RDM %s)", totoroName, account.getLongName(), account.getExternalId());
    }

    private TradeAccount findSuggestedAccount(DateMidnight tradeDate, TradeAccount invalidAccount, String valuationFunction) {
        String otherLegalEntity = findOtherLegalEntity(invalidAccount);
        String lvcc = invalidAccount.getAliasValue(TradeAccount.AccountAlias.LVCC);


        TradeAccount suggestedAccount = null;

        if (lvcc != null && otherLegalEntity != null) {
            suggestedAccount = repository.getRegion(TradeAccount.class).where()
                    .eq(TradeAccountFields.NOMURA_COMPANY, otherLegalEntity)
                    .eq(TradeAccount.AccountAlias.LVCC, lvcc)
                    .eq(TradeAccountFields.STATUS, "ACTIVE").selectFirst();
        }

        if (suggestedAccount == null) {
            Integer obligorId = TradeAccount.ExternalFields.CURRENT_OBLIGOR_ORG_ID.getValue(invalidAccount);
            if (obligorId == null) {
                return null;
            }

            suggestedAccount = repository.getRegion(TradeAccount.class).where().
                    eq(TradeAccountFields.NOMURA_COMPANY, otherLegalEntity)
                    .eq(TradeAccountFields.SUB_TYPE, "MASTER")
                    .eq(TradeAccountFields.STATUS, "ACTIVE")
                    .eq(TradeAccount.ExternalFields.CURRENT_OBLIGOR_ORG_ID, obligorId).selectFirst();
            if (suggestedAccount == null) {
                return null;
            }
        }

        ProductCounterpartyValidationResult validationResult = isValid(tradeDate, suggestedAccount, valuationFunction);
        if (validationResult.isValid()) {
            return suggestedAccount;
        }
        return null;
    }


    private ProductCounterpartyValidationResult isValid(DateMidnight tradeDate, TradeAccount account, String productType) {

        if(!"NFPS".equals(account.getNomuraCompany())) {
            return ProductCounterpartyValidationResult.newValidInstance();
        }

        if ("true".equalsIgnoreCase(account.getAttributeValue(SKIP_VALIDATION_ATTRIBUTE)) ) {
            return ProductCounterpartyValidationResult.newValidInstance();
        }
        Organisation owningOrg = account.getOwningOrg();

        //Organisation owningOrg = repository.getRegion(Organisation.class).where().eq(OrganisationFields.ORGANISATION_ID, orgId).eq(OrganisationFields.STATUS, "ACTIVE").selectFirst();
        OrganisationProductType p = getOrganisationProductType(owningOrg, productType);

        if(p == null){
            String parentProduct = getProductParent(productType);
            if (parentProduct != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("falling back to parent product");
                }
                return isValid(tradeDate, account, parentProduct);
            } else {
                return ProductCounterpartyValidationResult.newError(String.format("%s is not valid for trading %s in %s. Please consider booking the trade facing another entity.", buildAccountNameForErrorMessage(account), productType, account.getNomuraCompany()) );
            }
        }

        Date validFrom = !Strings.isNullOrEmpty(p.getFromDate()) ? DateUtils.parseDate(p.getFromDate()) : null;

        Date validTo = !Strings.isNullOrEmpty(p.getToDate()) ? DateUtils.parseDate(p.getToDate()) : null;

        if (validFrom == null) {
            return ProductCounterpartyValidationResult.newError(String.format("%s is not valid for trading %s in %s. Please consider booking the trade facing another entity.",buildAccountNameForErrorMessage(account), productType, account.getNomuraCompany()) );
        }

        if (validFrom != null && validFrom.getTime() > tradeDate.getMillis()) {
            return ProductCounterpartyValidationResult.newError(String.format("%s is not valid for trading %s in %s until %s. Please consider booking the trade facing another entity.", buildAccountNameForErrorMessage(account), productType, account.getNomuraCompany(), DateUtils.format(validFrom)) );
        }

        if (validTo != null && validTo.getTime() < tradeDate.getMillis() ) {
            return ProductCounterpartyValidationResult.newError(String.format("%s is not valid for trading %s in %s after %s", buildAccountNameForErrorMessage(account), productType, account.getNomuraCompany(), DateUtils.format(validTo)) );
        }

        return ProductCounterpartyValidationResult.newValidInstance();
    }

    private String getProductParent(String productType) {
        if (productType == null || productType.length() == 0) {
            return null;
        }
        int indexOfLastSeparator = productType.lastIndexOf(':');
        if (indexOfLastSeparator <= 0) {
            return null;
        }
        return productType.substring(0, indexOfLastSeparator);
    }

    private OrganisationProductType getOrganisationProductType(Organisation org, String productType){
        for(OrganisationProductType p : org.getOrganisationProductTypes()){
            if(p.getId().getProductTypeNarrative().equalsIgnoreCase(productType) &&
                    ("NFPSRules".equalsIgnoreCase(p.getId().getSystemCode()) || "NFPS".equalsIgnoreCase(p.getId().getSystemCode())))
                return p;
        }
        return null;
    }

    private static final TradeAccount.AccountAttribute SKIP_VALIDATION_ATTRIBUTE = new TradeAccount.AccountAttribute("SKIP_NFPS_VALIDATION");

}
