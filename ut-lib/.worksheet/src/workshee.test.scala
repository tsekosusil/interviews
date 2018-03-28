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
object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(395); 
  println("Welcome to the Scala worksheet");$skip(39); 
  implicit val conn: Connection = null;System.out.println("""conn  : org.apache.hadoop.hbase.client.Connection = """ + $show(conn ));$skip(50); 

  val msgRepo = new MessageRepository("message");System.out.println("""msgRepo  : hbasePersistent.MessageRepository = """ + $show(msgRepo ));$skip(316); val res$0 = 
                                                  
//case class Data (deviceId:UUID,location:Location,temperature:Int,time:Long) {}
//case class Location(latitude:Double,longitude:Double){}
                                                  
  println(msgRepo.objectToHbase(new Message(new Data(UUID.randomUUID()))));System.out.println("""res0: <error> = """ + $show(res$0))}
}
