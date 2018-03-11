package service

import payload.BookingRequest
import model.Booking
import persistent.UserDAO
import persistent.BookingDAO
import persistent.TravelDAO
import response.BookingResponse
import java.util.Date
import javax.inject._
import model._
import response._


sealed trait BookingService {
  //  case class Booking(bookingId:String,userId:String,travelCode:String,travelTimestamp:Date,bookingTimestamp:Date,deleted:Boolean) {
  //  def bookTransport(request: BookingRequest): Boolean
  def bookTransport(request: BookingRequest): Either[String,BookedTrip]

  def bookingsForUser(userId: String): Either[String, BookingResponse]

}

object BookingService {
  def apply(): BookingService = {
    val service = new BookingServiceImpl()
    service.mockData
    service
  }

  private class BookingServiceImpl extends BookingService {

    val userDao: UserDAO = new UserDAO();
    val bookingDao = new BookingDAO();
    val travelDao = new TravelDAO();

    //case class Travel(travelCode:String, departurePoint:String,  arrivalPoint:String)
    //case class Booking(bookingId:String,user:User,travel:Travel,travelTimestamp:Date,bookingTimestamp:Date) 

    def mockData = {
      val user1 = User("1", "user1@gmail.com", false)
      val user2 = User("2", "user2@gmail.com", false)
      val travel1 = Travel("fb-1", "Berlin", "Munich")
      val travel2 = Travel("fb-2", "Berlin", "Hamburg")
      val travel3 = Travel("fb-3", "Stuttgart", "Berlin")
      val travel4 = Travel("fb-4", "Munich", "Berlin")
      userDao.create(user1)
      userDao.create(user2)
      travelDao.create(travel1)
      travelDao.create(travel2)
      travelDao.create(travel3)
      travelDao.create(travel4)

      bookingDao.create(Booking("book-1",
        user1,
        travel1,
        new Date(),
        new Date()))

      bookingDao.create(Booking("book-2",
        user1,
        travel1,
        new Date(),
        new Date()))

    }

    //TODO booking timestamp needs to be stored on which timezone?
    override def bookTransport(request: BookingRequest): Either[String,BookedTrip] = {
      val user = userDao.findOne(user => user.email == request.email)
      println("FOUND user = " + user);
      val travel = travelDao.findOne(travel => travel.travelCode == request.travelCode)
      println("FOUND travel = " + travel);

      for (a <- 1 to request.noOfSeat if user.isDefined && travel.isDefined) {
        val booking = Booking("", user.get, travel.get, request.travelTimestamp, new Date(System.currentTimeMillis()));
        val newBooking = bookingDao.create(booking);
        val bookingResponse = BookingResponse.toBookedTrip(newBooking)
        return Right(bookingResponse)
      }
      
      if(travel.isEmpty) Left("Travel code is not valid")
      else if(user.isEmpty) Left("user is not registered")
      else Left("unknown")
    }

    override def bookingsForUser(userId: String): Either[String, BookingResponse] = {
      val user = userDao.findOne(curUser => curUser.userId.equals(userId))
      if (user.isDefined) {
        val bookingBean = bookingDao.filter(booking => booking.user.userId == userId)
        val bookingResponse = BookingResponse.toBookingResponse(user.get, bookingBean)
        Right(bookingResponse)
      } else {
        Left("User not registered")
      }
    }
  }
}