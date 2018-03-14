package persistentTwo

import model.Booking
import java.util.Date

class BookingDAO2 extends BaseMapMockDAO[String, Booking] {
  override def create(booking: Booking): Booking = {
    val bookingId: String = booking.user.userId.hashCode() + "-" + System.currentTimeMillis();

    val newBooking = Booking(bookingId, booking.user, booking.travel, booking.travelTimestamp, new Date(System.currentTimeMillis()),booking.noOfSeat)
    super.create(newBooking)
  }
}