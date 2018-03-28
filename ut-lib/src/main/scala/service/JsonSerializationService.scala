package service

import model._
import play.api.libs.json._
object JsonSerializationService {
  implicit val locationWriter = Json.format[Location]
  implicit val dataWriter = Json.format[Data]
  implicit val messageWritter = Json.format[Message]
  implicit val dailyAggregationWriter = Json.format[DailyAggregationOrder]
  
  implicit val messageSerializer: Message => String = message => Json.toJson(message).toString();
  implicit val messageDeserializer: String => Message = str => Json.parse(str).as[Message]
  
  implicit val dailyAggregationSerializer: DailyAggregationOrder => String = msg => Json.toJson(msg).toString()
  implicit val dailyAggregationDeserializer: String => DailyAggregationOrder = str => Json.parse(str).as[DailyAggregationOrder]


}