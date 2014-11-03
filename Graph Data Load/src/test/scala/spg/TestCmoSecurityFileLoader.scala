package spg

import java.io.{PrintWriter, File}

import org.scalatest.{FlatSpec, Matchers}
import spg.etl.CmoFileDataSource
import spg.util.GraphDatabaseTestHelper

class TestCmoSecurityFileLoader extends FlatSpec with Matchers with GraphDatabaseTestHelper{

  val date = "20140721"
  val CMO_SECURITIES = "\\\\spgnasprd.us.nomura.com\\mbsdata$\\mbsdata\\prod\\solr-security-feed\\data\\cmo." + date + ".zip"

  behavior of "Cmo Security File Loader"

    it should "Load a complete file" in {

      val loader = new CmoFileDataSource(new File(CMO_SECURITIES))
      val pw = new PrintWriter(System.out)
      //loader.processFile(pw)
      pw.flush()
      pw.close()


    }


}
