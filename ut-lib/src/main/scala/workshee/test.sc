package workshee

import model._
import org.apache.hadoop.hbase._
import org.apache.hadoop.hbase.client._

import org.apache.hadoop.hbase.util._
import java.util.Properties
import java.lang.reflect.ParameterizedType;
import annotation._
import scala.collection.JavaConversions.seqAsJavaList
import hbasePersistent._
import java.util.UUID
object test {
  println("Welcome to the Scala worksheet")
  implicit val conn: Connection = null

  val msgRepo = new MessageRepository("message")
                                                  
//case class Data (deviceId:UUID,location:Location,temperature:Int,time:Long) {}
//case class Location(latitude:Double,longitude:Double){}
                                                  
  println(msgRepo.objectToHbase(new Message(new Data(UUID.randomUUID()))))
}