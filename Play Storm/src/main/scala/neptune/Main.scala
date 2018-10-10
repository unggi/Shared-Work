package neptune

import java.util.logging.Logger

import org.apache.storm.trident.TridentTopology
import org.apache.storm.trident.operation.builtin.Count
import org.apache.storm.trident.operation.{BaseFunction, TridentCollector}
import org.apache.storm.trident.testing.FixedBatchSpout
import org.apache.storm.trident.tuple.TridentTuple
import org.apache.storm.tuple.{Fields, Values}
import org.apache.storm.{Config, LocalCluster}

/**
  * Created by unggi on 6/4/17.
  */
object Main {

  def main(args: Array[String]): Unit = {
    System.err.println("Hello World")

    // Create a spout to generate new trades which will be accumulated as positions


    object Trade {
      val fields = new Fields("book", "secid", "quantity", "price")
    }

    case class Trade(book: Int, cusip: String, quantity: Double, price: Double)
      extends Values(new Integer(book), cusip, Double.box(quantity), Double.box(price)) {
    }

    object Position {
      val fields = new Fields("book", "secid", "notional", "price", "marketValue")
    }

    new Count()
    val spout = new FixedBatchSpout(Trade.fields, 3,
      Trade(603, "93209301", 100000000.0, 99.0),
      Trade(604, "93834743", 50000000.0, 102.0),
      Trade(605, "83762717", 30000000.0, 101.0),
      Trade(606, "93939932", 25000000.0, 100.0),
      Trade(607, "65262526", 10000000.0, 89.0)
    )
    spout.setCycle(true)

    val topology = new TridentTopology()
    topology
      .newStream("trades", spout)
      .each(Trade.fields, new GetPosition(), Position.fields)
      .groupBy(new Fields("book", "secid"))
      .name("Trades")

    val storm = topology.build()

    val conf = new Config()
    conf.setDebug(false)
    conf.setNumWorkers(2)

    val cluster = new LocalCluster()
    cluster.submitTopology("test", conf, storm)
    Thread.sleep(5000)
    cluster.killTopology("test")
    cluster.shutdown()

  }

  class GetPosition() extends BaseFunction {
    val log = Logger.getLogger("BuildPosition")
    override def execute(tuple: TridentTuple, collector: TridentCollector): Unit = {
      log.info(tuple.toString)
      collector.emit(new Values(tuple.get(2)))
    }

  }


}

