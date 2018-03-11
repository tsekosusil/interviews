package persistent
import model.Booking
import scala.collection.mutable.ListBuffer

import java.util.Date
class BookingDAO extends BaseMockDAO[Booking] {

  override def create(booking: Booking): Booking = {
    val bookingId: String = booking.user.userId.hashCode() + "-" + System.currentTimeMillis();
    println("OLD BOOKING = " + booking)

    val newBooking = Booking(bookingId, booking.user, booking.travel, booking.travelTimestamp, new Date(System.currentTimeMillis()))
    println("NEW BOOKING = " + newBooking)
    super.create(newBooking)
  }
}

