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
import java.util.Date
import java.time._
object Test {

  def getStartAndEndOfDayInMilis(date: Date): (Long, Long) = {
    //Might want to manage the timezone in real scenario.
    val localTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val todayMidnight = localTime.atStartOfDay()
    val tommorowMidnight = todayMidnight.plusDays(1)
    println(todayMidnight)
    println(tommorowMidnight)
    val x = Date.from(todayMidnight.atZone(ZoneId.systemDefault()).toInstant()).getTime
    val y = Date.from(tommorowMidnight.atZone(ZoneId.systemDefault()).toInstant()).getTime
    (x,y)
  }
  def main(args: Array[String]) {
    implicit val conn: Connection = null

    val msgRepo = new MessageRepository("message")

    //case class Data (deviceId:UUID,location:Location,temperature:Int,time:Long) {}
    //case class Location(latitude:Double,longitude:Double){}

    println(msgRepo.objectToHbase(new Message(new Data(UUID.randomUUID(),
                                                        new Location(34.124,42.112),
                                                        42,
                                                        144552l
          
    ))))
    
     val sl = DAILY
     println(sl)
    
    println(UUID.fromString("97b0d530-f308-49ef-89af-a1ef92659d86"))
    
    val test = Data(UUID.randomUUID(), Location(34.25,42.55),30, System.currentTimeMillis())
    println("DATA =  "  + test.prettyDate)

   val (x,y) =  getStartAndEndOfDayInMilis(new Date())
   println("x = "+new Date(x))
   println("y = "+new Date(y))
  }
}