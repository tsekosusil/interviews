package tests
import service.BookingService;
import play.api.libs.json.Json
import persistent._
import persistentTwo._
import model._
import java.util._
object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(210); 
  println("Welcome to the Scala worksheet");$skip(33); 
  val service = BookingService();System.out.println("""service  : service.BookingService = """ + $show(service ));$skip(47); 
  val bookings = service.bookingsForUser("1");System.out.println("""bookings  : Either[String,response.BookingResponse] = """ + $show(bookings ));$skip(523); ;
                                                  
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
      
      
    };System.out.println("""mockData: => Unit""")}

   
}
