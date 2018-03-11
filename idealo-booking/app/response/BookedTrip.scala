package response

import java.util.Date
import play.api.libs.json.Json
case class BookedTrip(bookingId: String, travelId: String, travelTime: Date, departure: String, arrival: String) {
  implicit val jsonFormatter = Json.format[BookedTrip]

}