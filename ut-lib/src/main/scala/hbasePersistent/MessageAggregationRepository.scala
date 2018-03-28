package hbasePersistent

import model._
import model._
import org.apache.hadoop.hbase._
import org.apache.hadoop.hbase.client._

import org.apache.hadoop.hbase.util._
import java.util.Properties
import java.lang.reflect.ParameterizedType;
import annotation._
import scala.collection.JavaConversions.seqAsJavaList
import java.util.UUID
import java.util.Date
import java.text.SimpleDateFormat
import java.time._
class MessageAggregationRepository(tableName: String)(implicit connection: Connection) extends AbstractPersistence[MessageAggregation](tableName)(connection) {

  def getDailyMessageAggregationFromMessage(message: Message): Option[MessageAggregation] = {
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd")
    val dailyKey = message.data.deviceId.toString() + "|" + dateFormat.format(new Date(message.data.time))
    val dailyRecord = Option(this.get(dailyKey))
    dailyRecord
  }

  def getGlobalMessageAggregationFromMessage(message: Message): Option[MessageAggregation] = {
    val dailyKey = message.data.deviceId.toString()
    val dailyRecord = Option(this.get(dailyKey))
    dailyRecord
  }

  override protected def createStorageConversion(): MessageAggregation => Put = message => {
    import java.text.SimpleDateFormat
    import java.util.Date
    val strDate = new SimpleDateFormat("yyyy-MM-dd")

    val rowKey = message.aggregationType match {
      case DAILY => message.uuid.toString() + "|" + strDate.format(new Date(message.lastUpdate))
      case _     => message.uuid.toString()
    }
    //val rowKey = 
    val put = new Put(rowKey)

    put.addColumn("cf", "deviceId", message.uuid.toString())
    put.addColumn("cf", "latitude", message.loc.latitude)
    put.addColumn("cf", "longitude", message.loc.longitude)
    put.addColumn("cf", "lastUpdate", message.lastUpdate)
    put.addColumn("cf", "maxTemp", message.maxTemp)
    put.addColumn("cf", "minTemp", message.minTemp)
    put.addColumn("cf", "noDataPoint", message.noDataPoint)
    put.addColumn("cf", "aggregationType", message.aggregationType.toString())

    put
  }
  override protected def createRetrivalConversion(): Result => MessageAggregation = result => {

    val rowKey: String = result.getRow
    val deviceId: String = result.getValue("cf", "deviceId")
    val latitude: Double = result.getValue("cf", "latitude")
    val longitude: Double = result.getValue("cf", "longitude")
    val timestamp: Long = result.getValue("cf", "timestamp")
    val maxTemp: Int = result.getValue("cf", "maxTemp")
    val minTemp: Int = result.getValue("cf", "minTemp")
    val noDataPoint: Int = result.getValue("cf", "noDataPoint")
    val lastUpdate: Long = result.getValue("cf", "lastUpdate")
    val aType: String = result.getValue("cf", "aggregationType")
    val aggregationType: AggregationType = aType match {
      case "DAILY" => DAILY
      case _       => GLOBAL
    }

    MessageAggregation(UUID.fromString(deviceId), Location(latitude, longitude), maxTemp, minTemp, noDataPoint, lastUpdate, aggregationType)

  }

}

object MessageAggregationRepository {
  implicit val connection: Connection = Implicits.connection
  implicit val repository: MessageAggregationRepository = new MessageAggregationRepository("messageAggregation")

}