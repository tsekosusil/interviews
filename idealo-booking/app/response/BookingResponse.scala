package response
import java.util.Date
import model.Booking
import model.User
import play.api.libs.json.Json
import java.text.{DateFormat,SimpleDateFormat}
case class BookingResponse(userId: String, email: String, trips: List[BookedTrip]) {

}
object BookingResponse {
  def toBookingResponse(user: User, bookings: List[Booking]): BookingResponse = {

    BookingResponse(user.userId, user.email, bookings.collect({ case elt: Booking => toBookedTrip(elt) }))

  }

 implicit def toBookedTrip(booking: Booking): BookedTrip = {
    val dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
    val strTimestamp = dateFormat.format(booking.travelTimestamp)
    BookedTrip(booking.bookingId, booking.travel.travelCode, strTimestamp, booking.travel.departurePoint, booking.travel.arrivalPoint)
  }

}