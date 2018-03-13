import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

import org.junit.Before
import org.junit.Test

import service.BookingService
import payload.BookingRequest
import java.util.Date

class ServiceTest {

  val bookingService = BookingService()

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
    val request = BookingRequest("user1@gmail.com","fb-1",new Date(),1);
    val bookedTrip = bookingService.bookTransport(request)
    val trips = bookingService.bookingsForUser("1")
    //the service return the booking
    assertTrue(bookedTrip.isRight)
    //booking must have booking id for retrival
    assertFalse(bookedTrip.right.get.bookingId.isEmpty())
    //booking is stored
    assertTrue(trips.right.get.trips.filter(curTrip => curTrip.bookingId.equals(bookedTrip.right.get.bookingId)).size>0)
  }
  
  @Test
  def testBookingUnknownEmail(){
    
  }
  
  @Test
  def testBookingUnknownTravelCode(){
    
  }
  
  @Test
  def testBookingWrongTravelTimestamp(){
    
  }
  
  @Test
  def testBookingWrongNoOfSeat(){
    
  }
  
 
}