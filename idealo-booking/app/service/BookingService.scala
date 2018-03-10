package service

import payload.BookingRequest
import model.Booking
import persistent.UserDAO
import persistent.BookingDAO
import persistent.TravelDAO
import response.BookingResponse
import java.util.Date
import javax.inject._
import model.User
import model.Travel

sealed trait BookingService {
  //  case class Booking(bookingId:String,userId:String,travelCode:String,travelTimestamp:Date,bookingTimestamp:Date,deleted:Boolean) {
  def bookTransport(request: BookingRequest): Boolean
  def bookingsForUser(userId: String): List[BookingResponse]

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
    override def bookTransport(request: BookingRequest): Boolean = {
      val user = userDao.findOne(user => user.email == request.email)
      val travel = travelDao.findOne(travel => travel.travelCode == request.travelCode)
      for (a <- 1 to request.noOfSeat if user.isDefined && travel.isDefined) {
        val booking = Booking("", user.get, travel.get, request.travelTimestamp, new Date(System.currentTimeMillis()));
        bookingDao.create(booking);
      }
      true
    }

    override def bookingsForUser(userId: String): List[BookingResponse] = {
      val bookingBean = bookingDao.filter(booking => booking.user.userId == userId)
      bookingBean.collect { case elt: Booking => BookingResponse.toBookingResponse(elt) }
    }
  }
}