package service
import java.util.UUID
import model._
import play.api.libs.json._
import hbasePersistent._
import org.apache.hadoop.hbase._
import org.apache.hadoop.hbase.client._
trait MessageService {
  def generateRandomMessage(uuid: UUID): Message
//  def getDailyMessageAggregation(message: Message): MessageAggregation
//  def getGlobalMessageAggregation(message: Message): MessageAggregation
  def storeMessageBulk(messages: Iterator[Message])
  def calculateAggregationMessage(order: DailyAggregationOrder): MessageAggregation

}

private class MessageServiceImpl(implicit messageRepository: MessageRepository, messageAggregationRepository: MessageAggregationRepository) extends MessageService {

  implicit val locationWriter = Json.format[Location]
  implicit val dataWriter = Json.format[Data]
  implicit val messageWritter = Json.format[Message]

  private def createRandomLocation(): Location = {

    val minLat = -90.00;
    val maxLat = 90.00;
    val latitude = minLat + (Math.random() * ((maxLat - minLat) + 1));
    val minLon = 0.00;
    val maxLon = 180.00;
    val longitude = minLon + (Math.random() * ((maxLon - minLon) + 1));

    val loc = new Location(latitude, longitude)

    return loc;
  }

  override def storeMessageBulk(messages: Iterator[Message]) {
    messageRepository.save(messages.toList)
  }
  override def generateRandomMessage(uuid: UUID): Message = {
    val data: Data = new Data(uuid, createRandomLocation, Math.round(Math.random().toFloat * 40), System.currentTimeMillis);
    new Message(data)
  }

//  override def getDailyMessageAggregation(data: Message): MessageAggregation = {
//    //TODO BAD approach, row key construction must be transparent from service layer, to refactor to persistent layer!!!
//    val dailyRecord = messageAggregationRepository.getDailyMessageAggregationFromMessage(data)
//    val newRecord: MessageAggregation = dailyRecord.map(_.aggregateMessage(data)).getOrElse(
//      MessageAggregation(data.data.deviceId, data.data.location, data.data.temperature, data.data.temperature, data.data.temperature, data.data.time, DAILY))
//    newRecord
//  }
//  override def getGlobalMessageAggregation(data: Message): MessageAggregation = {
//    //TODO BAD approach, row key construction must be transparent from service layer!!!
//    val globalRecord = messageAggregationRepository.getGlobalMessageAggregationFromMessage(data)
//    val newRecord: MessageAggregation = globalRecord.map(_.aggregateMessage(data)).getOrElse(
//      MessageAggregation(data.data.deviceId, data.data.location, data.data.temperature, data.data.temperature, data.data.temperature, data.data.time, GLOBAL))
//    newRecord
//  }

  override def calculateAggregationMessage(order: DailyAggregationOrder): MessageAggregation =
    {
      val messageStartRowKey = order.uuidStr + "|" + order.timestampStart
      val messageEndRowKey = order.uuidStr + "|" + order.timestampEnd
      val messages = messageRepository.scan(messageStartRowKey, messageEndRowKey)
      val sample = messages.head
      val temps = messages.map(_.data.temperature)
      val minTemp = temps.min
      val maxTemp = temps.max
      val noRecord = temps.size
      val newRecord = MessageAggregation(UUID.fromString(order.uuidStr),sample.data.location,maxTemp,minTemp,noRecord,System.currentTimeMillis(),DAILY)
      messageAggregationRepository.save(newRecord)
      newRecord
    }

}
object MessageService {
  //encapsulate the creation of the service from outside layer
  def apply(): MessageService = new MessageServiceImpl()
  implicit def create: MessageService = MessageService()
}