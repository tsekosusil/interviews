package response

import java.util.Date
import play.api.libs.json.Json
case class BookedTrip(bookingId: String, travelId: String, travelTime: String, departure: String, arrival: String) {

}