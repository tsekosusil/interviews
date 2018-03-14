package tests
import service.BookingService;
import play.api.libs.json.Json
import persistent._
import persistentTwo._
import model._
import java.util._
object test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val service = BookingService()                  //> OLD BOOKING = Booking(book-1,User(1,user1@gmail.com,false),Travel(fb-1,Berli
                                                  //| n,Munich),Wed Mar 14 17:00:17 CET 2018,Wed Mar 14 17:00:17 CET 2018)
                                                  //| NEW BOOKING = Booking(49-1521043217633,User(1,user1@gmail.com,false),Travel(
                                                  //| fb-1,Berlin,Munich),Wed Mar 14 17:00:17 CET 2018,Wed Mar 14 17:00:17 CET 201
                                                  //| 8)
                                                  //| OLD BOOKING = Booking(book-2,User(1,user1@gmail.com,false),Travel(fb-1,Berli
                                                  //| n,Munich),Wed Mar 14 17:00:17 CET 2018,Wed Mar 14 17:00:17 CET 2018)
                                                  //| NEW BOOKING = Booking(49-1521043217668,User(1,user1@gmail.com,false),Travel(
                                                  //| fb-1,Berlin,Munich),Wed Mar 14 17:00:17 CET 2018,Wed Mar 14 17:00:17 CET 201
                                                  //| 8)
                                                  //| service  : service.BookingService = service.BookingService$BookingServiceImp
                                                  //| l@66a3ffec
  val bookings = service.bookingsForUser("1");    //> bookings  : Either[String,response.BookingResponse] = Right(BookingResponse(
                                                  //| 1,user1@gmail.com,List(BookedTrip(49-1521043217633,fb-1,Wed Mar 14 17:00:17 
                                                  //| CET 2018,Berlin,Munich), BookedTrip(49-1521043217668,fb-1,Wed Mar 14 17:00:1
                                                  //| 7 CET 2018,Berlin,Munich))))
                                                  
  // val json = Json.toJson(bookings)
  
   
   
 def mockData = {
    val userDao: UserDAO2 = new UserDAO2();
    val bookingDao = new BookingDAO2();
    val travelDao = new TravelDAO();
    val travelScheduleDao = new TravelScheduleDAO();
 
      val user1 = User("1", "user1@gmail.com", false)
      val user2 = User("2", "user2@gmail.com", false)
      
      userDao.create(user1)
      userDao.create(user2)
      
      println(userDao.findAll)
      
      
    }                                             //> mockData: => Unit

   
}