package tests
import service.BookingService;
import play.api.libs.json.Json
object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(133); 
  println("Welcome to the Scala worksheet");$skip(33); 
  val service = BookingService();System.out.println("""service  : service.BookingService = """ + $show(service ));$skip(47); 
  val bookings = service.bookingsForUser("1");System.out.println("""bookings  : List[response.BookingResponse] = """ + $show(bookings ));$skip(87); ;
                                                  
   val json = Json.toJson(bookings);System.out.println("""json  : play.api.libs.json.JsValue = """ + $show(json ))}
}
