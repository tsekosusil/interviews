package service

import payload.BookingRequest
import model.Booking

import response.BookingResponse
import java.util.Date
import javax.inject._
import model._
import response._
import persistentTwo.BookingRepository
import persistentTwo.TravelRepository
import persistentTwo.TravelScheduleRepository
import persistentTwo.UserRepository
import util.Constants._
import java.util.UUID

sealed trait BookingService {

  def bookTransport(request: BookingRequest): Either[String, BookedTrip]

  def bookingsForUser(userId: String): Either[String, BookingResponse]

}

object BookingService {
  def apply(): BookingService = {
    val service = new BookingServiceImpl()
    service.mockData
    service
  }

  private class BookingServiceImpl(implicit userDao:UserRepository,
                                            bookingDao:BookingRepository,
                                            travelDao:TravelRepository,
                                            travelScheduleDao:TravelScheduleRepository) extends BookingService {


    def mockData = {
      val user1 = User("1", "user1@gmail.com", false)
      val user2 = User("2", "user2@gmail.com", false)
      val travel1 = Travel("fb-1", "Berlin", "Munich")
      val travel2 = Travel("fb-2", "Berlin", "Hamburg")
      val travel3 = Travel("fb-3", "Stuttgart", "Berlin")
      val travel4 = Travel("fb-4", "Munich", "Berlin")

      val travelSchedule1 = TravelSchedule(UUID.randomUUID().toString(), "fb-1", new Date(1521050400000l), 2)
      val travelSchedule2 = TravelSchedule(UUID.randomUUID().toString(), "fb-1", new Date(1521136800000l), 30)

      val travelSchedule3 = TravelSchedule(UUID.randomUUID().toString(), "fb-2", new Date(1521048600000l), 4)
      val travelSchedule4 = TravelSchedule(UUID.randomUUID().toString(), "fb-2", new Date(1521135000000l), 50)

      val travelSchedule5 = TravelSchedule(UUID.randomUUID().toString(), "fb-3", new Date(1521034200000l), 1)
      val travelSchedule6 = TravelSchedule(UUID.randomUUID().toString(), "fb-3", new Date(1521120600000l), 40)

      val travelSchedule7 = TravelSchedule(UUID.randomUUID().toString(), "fb-4", new Date(1521016200000l), 2)
      val travelSchedule8 = TravelSchedule(UUID.randomUUID().toString(), "fb-4", new Date(1521102600000l), 13)

      userDao.create(user1)
      userDao.create(user2)
      travelDao.create(travel1)
      travelDao.create(travel2)
      travelDao.create(travel3)
      travelDao.create(travel4)

      travelScheduleDao.create(travelSchedule1)
      travelScheduleDao.create(travelSchedule2)
      travelScheduleDao.create(travelSchedule3)
      travelScheduleDao.create(travelSchedule4)
      travelScheduleDao.create(travelSchedule5)
      travelScheduleDao.create(travelSchedule6)
      travelScheduleDao.create(travelSchedule7)
      travelScheduleDao.create(travelSchedule8)

      println(travelScheduleDao.findAll)

      bookingDao.create(Booking("book-1",
        user1,
        travel1,
        new Date(),
        new Date(), 1))

      bookingDao.create(Booking("book-2",
        user1,
        travel1,
        new Date(),
        new Date(), 1))

    }

    //TODO put the num of seat on booking instance. 
    override def bookTransport(request: BookingRequest): Either[String, BookedTrip] = {
      if (request.noOfSeat <= 0) Left(INVALID_SEAT_ARGUMENT_ERROR)
      else {
        println(request.travelTimestamp)
        val user = userDao.findOne(user => user.email == request.email)
        val travel = travelDao.findOne(travel => travel.travelCode == request.travelCode)
        val travelSchedule = travelScheduleDao.findOne(ts => ts.travelId.equals(request.travelCode) &&
          ts.travelTimestamp.equals(request.travelTimestamp) &&
          ts.seatAvailable >= request.noOfSeat)

        if (travel.isEmpty) Left(TRAVEL_CODE_NOT_VALID_ERROR)
        else if (user.isEmpty) Left(USER_NOT_REGISTERED_ERROR)
        else if (travelSchedule.isEmpty) Left(NO_SEAT_LEFT_ERROR)
        else {
          val booking = Booking("", user.get, travel.get, request.travelTimestamp, new Date(System.currentTimeMillis()), 1);
          val newBooking = bookingDao.create(booking);
          val bookingResponse = BookingResponse.toBookedTrip(newBooking)
          val tsBean = travelSchedule.get
          val newTravelSchedule = TravelSchedule(tsBean.key, tsBean.travelId, tsBean.travelTimestamp, tsBean.seatAvailable - request.noOfSeat)
          travelScheduleDao.update(newTravelSchedule)
          return Right(bookingResponse)
        }
      }
    }

    override def bookingsForUser(userId: String): Either[String, BookingResponse] = {
      val user = userDao.findOne(curUser => curUser.userId.equals(userId))
      if (user.isDefined) {
        val bookingBean = bookingDao.filter(booking => booking.user.userId == userId)
        val bookingResponse = BookingResponse.toBookingResponse(user.get, bookingBean)
        Right(bookingResponse)
      } else {
        Left(USER_NOT_REGISTERED_ERROR)
      }
    }
  }
}