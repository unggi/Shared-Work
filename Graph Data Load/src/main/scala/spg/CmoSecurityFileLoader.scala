package spg

import java.io.File
import org.neo4j.graphdb.{GraphDatabaseService, DynamicLabel}


class CmoSecurityFileLoader(override val sourcePath: File, db: GraphDatabaseService) extends CSVFileLoader {

 override def delimiter: Char = '|'

  override def progressInterval: Int = 10000

  //
  // Map Field Names to Model fields
  //
  mapColumns(classOf[CmoBean],
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

  override def processRow(line: Int, data: Array[String]): Unit = {
    val label = DynamicLabel.label("Security")

    val node = db.createNode(label)
    val bean = new CmoBean()

    rowToBean(bean, node, data)
  }

}
