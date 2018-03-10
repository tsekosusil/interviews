package tests
import service.BookingService;
import play.api.libs.json.Json
object test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val service = BookingService()                  //> service  : service.BookingService = service.BookingService$BookingServiceImp
                                                  //| l@6f7fd0e6
  val bookings = service.bookingsForUser("1");    //> bookings  : List[response.BookingResponse] = List(BookingResponse(book-1,1,f
                                                  //| b-1,Sat Mar 10 07:06:03 CET 2018,Berlin,Munich), BookingResponse(book-2,1,fb
                                                  //| -1,Sat Mar 10 07:06:03 CET 2018,Berlin,Munich))
                                                  
   val json = Json.toJson(bookings)               //> json  : play.api.libs.json.JsValue = [{"bookingId":"book-1","userId":"1","tr
                                                  //| avelId":"fb-1","travelTime":1520661963374,"departure":"Berlin","arrival":"Mu
                                                  //| nich"},{"bookingId":"book-2","userId":"1","travelId":"fb-1","travelTime":152
                                                  //| 0661963374,"departure":"Berlin","arrival":"Munich"}]
}