package response
import java.util.Date
import model.Booking
import model.User
import play.api.libs.json.Json

case class BookingResponse(userId: String, email: String, trips: List[BookedTrip]) {

}
object BookingResponse {
  implicit def toBookingResponse(user: User, bookings: List[Booking]): BookingResponse = {

    BookingResponse(user.userId, user.email, bookings.collect({case elt: Booking => toBookedTrip(elt)}))

  }

  //  case class BookedTrip(bookingId: String, travelId: String, travelTime: Date, departure: String, arrival: String) {

  def toBookedTrip(booking: Booking): BookedTrip = {
    BookedTrip(booking.bookingId, booking.travel.travelCode, booking.travelTimestamp, booking.travel.departurePoint, booking.travel.arrivalPoint)
  }

}