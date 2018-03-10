package response
import java.util.Date
import model.Booking
import play.api.libs.json.Json

case class BookingResponse(bookingId:String,userId: String, travelId: String, travelTime: Date, departure: String, arrival: String) {

}
object BookingResponse {
  implicit def toBookingResponse(bookingBean: Booking): BookingResponse = {
    BookingResponse(bookingBean.bookingId,
                    bookingBean.user.userId,
                    bookingBean.travel.travelCode,
                    bookingBean.travelTimestamp,
                    bookingBean.travel.departurePoint,
                    bookingBean.travel.arrivalPoint)
  }
  
    implicit val bookingJsonFormat = Json.format[BookingResponse]

}