package spg.etl

import java.io.File

import org.neo4j.graphdb.{DynamicLabel, GraphDatabaseService}
import spg.datamodel.Cmo
import spg.etl.csv.{CSVDataSource, CSVBatchDriver}


class CmoFileDataSource(csvPath: File) extends CSVDataSource[Cmo](csvPath, classOf[Cmo]) {

 override def delimiter: Char = '|'

  //
  // Map Field Names to Model fields
  //
  mapColumns(
    "ESMP" -> "ESMID",
    "CSP" -> "CUSIP",
    "SecurityType" -> "SecurityType",
    "AnnouncementDate" -> "AnnouncementDate",
    "Description" -> "Description",
    "IsRule144A" -> "IsRule144A",
    "IssueDate" -> "IssueDate",
    "ParValue" -> "ParValue",
    "Series" -> "Series",
    "IssuerName" -> "IssuerName",
    "CollateralType" -> "CollateralType",
    "FirstSettlementDate" -> "FirstSettlementDate",
    "CouponDividendFrequency" -> "CouponDividendFrequency",
    "CouponDividendRate" -> "CouponDividendRate",
    "DatedDate" -> "DatedDate",
    "LastPayDate" -> "LastPayDate",
    "CurrentMaturityDate" -> "CurrentMaturityDate",
    "PrincipalAmountOutstanding" -> "PrincipalAmountOutstanding",
    "FirstCouponDate" -> "FirstCouponDate",
    "TrancheClass" -> "TrancheClass",
    "IOPOFlag" -> "IOPOFlag",
    "MortgageType" -> "MortgageType",
    "DelayDays" -> "DelayDays",
    "OriginalAmountOfPrincipal" -> "OriginalAmountOfPrincipal",
    "CurrentFactor" -> "CurrentFactor",
    "CurrentFactorEffectiveDate" -> "CurrentFactorEffectiveDate",
    "IsMtgePaidOff" -> "IsMtgePaidOff",
    "MtgeCMOGroup" -> "MtgeCMOGroup",
    "IsRegS" -> "IsRegS",
    "BMT" -> "BMT",
    "ISIN" -> "ISIN",
    "dataSource" -> "dataSource",
    "dataTimestamp" -> "dataTimestamp",
    "SEC_ID" -> "SEC_ID",
    "MktSector" -> "MktSector"
  )
}
