package hbasePersistent

import org.apache.hadoop.hbase._
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.util._
import java.util.Properties
import java.lang.reflect.ParameterizedType;
import annotation._
import scala.collection.JavaConversions.seqAsJavaList
import org.apache.hadoop.hbase.HConstants;

object Implicits {

  private def getProperty: Properties = {
    val prop = new Properties()
    val is = Implicits.getClass.getResourceAsStream("/application.properties")
    prop.load(is)
    return prop
  }

  val prop = getProperty


  def readConfig: Configuration = {
    val conf = HBaseConfiguration.create();
    conf.set("hbase.zookeeper.quorum", "localhost");
    conf
  }

  private def getConnection: Connection = {
    val conf = Implicits.readConfig
    println(conf)
    val conn = ConnectionFactory.createConnection(conf)
    println(conn)
    conn
  }

  implicit val connection = getConnection
}