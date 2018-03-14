import java.util.Date

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

import payload.BookingRequest
import service.BookingService
import persistentTwo._

class ServiceTest {

  val bookingService = BookingService()
  val travelScheduleDao = new TravelScheduleDAO2()

  @Before
  def init() {

  }

  @Test
  def testCheckBookingOk() {
    val result = bookingService.bookingsForUser("1");
    assertTrue(result.isRight)

  }

  @Test
  def testCheckBookingUnknownUser() {
    val result = bookingService.bookingsForUser("shinchan");
    assertTrue(result.isLeft)
    assertTrue(result.left.get.equals("User not registered"));
  }

  @Test
  def testCheckBookingNullArg() {
    val result = bookingService.bookingsForUser(null);
    assertTrue(result.isLeft)
    assertTrue(result.left.get.equals("User not registered"));

  }

  //  email:String,
  //  travelCode:String,
  //  travelTimestamp:Date,
  //  noOfSeat:Int
  //  
  @Test
  def testBookingOk() {
    val travelDate = new Date(1521050400000l)
    println(travelDate)
    val request = BookingRequest("user1@gmail.com", "fb-1", travelDate, 1);
    val bookedTrip = bookingService.bookTransport(request)
    val trips = bookingService.bookingsForUser("1")
    println(bookedTrip)
    //the service return the booking
    assertTrue(bookedTrip.isRight)
    //booking must have booking id for retrival
    assertFalse(bookedTrip.right.get.bookingId.isEmpty())
    //booking is stored
    assertTrue(trips.right.get.trips.filter(curTrip => curTrip.bookingId.equals(bookedTrip.right.get.bookingId)).size > 0)
  }

  @Test
  def testBookingUnknownEmail() {
    val request = BookingRequest("shinchan@gmail.com", "fb-1", new Date(), 1);
    val bookedTrip = bookingService.bookTransport(request)
    val trips = bookingService.bookingsForUser("1")
    //the service return the booking
    assertTrue(bookedTrip.isLeft)
    assertTrue(bookedTrip.left.get.equals("User not registered"))
  }

  @Test
  def testBookingUnknownTravelCode() {

  }

  @Test
  def testBookingWrongTravelTimestamp() {

  }

  @Test
  def testBookingWrongNoOfSeat() {

  }

  @Test
  def testBookingOverbookedTravel() {
    val travelDate = new Date(1521050400000l)
    println(travelDate)
    val request = BookingRequest("user1@gmail.com", "fb-1", travelDate, 5);
    val bookedTrip = bookingService.bookTransport(request)
    val trips = bookingService.bookingsForUser("1")
    println(bookedTrip)
    //the service return the booking
    assertTrue(bookedTrip.isLeft)
    //booking must have booking id for retrival
    assertTrue(bookedTrip.left.get.equals("There are seat left for this travel"))

  }

}