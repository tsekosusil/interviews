package payload

import java.util.Date
import play.api.libs.json.Json

case class BookingRequest(email:String,travelCode:String,travelTimestamp:Date,noOfSeat:Int){
  implicit val formatter = Json.format[BookingRequest]

}