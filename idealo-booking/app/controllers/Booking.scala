package controllers

import play.api.mvc.Controller;
import play.api.mvc.Action;
import play.api.libs.json.Json

import service.BookingService;
class Booking extends Controller {
  
  val bookingService = BookingService()
  
  def commit = Action {
    Ok("Commit method");
  }

  //TODO set up the path param
  //curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://www.localhost.com:9000/booking

  def bookings = Action {
   val bookings = bookingService.bookingsForUser("1")
//    Ok("OK")
   Ok(Json.toJson(bookings));

  }

}