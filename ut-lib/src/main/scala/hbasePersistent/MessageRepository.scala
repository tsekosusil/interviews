package hbasePersistent

import model._
import org.apache.hadoop.hbase._
import org.apache.hadoop.hbase.client._

import org.apache.hadoop.hbase.util._
import java.util.Properties
import java.lang.reflect.ParameterizedType;
import annotation._
import scala.collection.JavaConversions.seqAsJavaList
import java.util.UUID

class MessageRepository(tableName: String)(implicit connection: Connection) extends AbstractPersistence[Message](tableName)(connection) {
  

  
  override protected def createStorageConversion(): Message => Put = message => {
    val put = new Put(message.data.deviceId + "|" + message.data.time)

    put.addColumn("cf", "deviceId", message.data.deviceId.toString())
    put.addColumn("cf", "latitude", message.data.location.latitude)
    put.addColumn("cf", "longitude", message.data.location.longitude)
    put.addColumn("cf", "timestamp", message.data.time)
    put.addColumn("cf", "temprature", message.data.temperature)
    put

  }
  override protected def createRetrivalConversion(): Result => Message = result => {
    val rowKey: String = result.getRow
    val deviceId: String = result.getValue("cf", "deviceId")
    val latitude: Double = result.getValue("cf", "latitude")
    val longitude: Double = result.getValue("cf", "longitude")
    val timestamp: Long = result.getValue("cf", "timestamp")
    val temprature: Int = result.getValue("cf", "temperature")

    Message(Data(UUID.fromString(deviceId), Location(latitude, longitude), temprature, timestamp))
  }
}

object MessageRepository {
  implicit val connection: Connection = Implicits.connection
  implicit val repository: MessageRepository = new MessageRepository("message")
}