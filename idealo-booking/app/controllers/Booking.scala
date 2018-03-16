package controllers

import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.mvc.Controller
import response.BookingResponse
import service.BookingService
import payload.BookingRequest
import response.BookedTrip
import play.api.libs.json._
import play.api.mvc._
class Booking extends Controller with ResponseCreator {

  val bookingService = BookingService()

  //  curl -H "Content-type: application/json" -X POST -d '{"email":"user2@gmail.com","travelCode":"fb-1","travelTimestamp":"2018-04-09 09:00:00","noOfSeat":2}' http://localhost:9000/commit
  implicit val requestFormatter = Json.format[BookingRequest]
  implicit val jsonFormatter = Json.format[BookedTrip]
  implicit val bookingRespnseFormatter = Json.format[BookingResponse]


  def commit() = Action {
    request =>
      {
        val jsValue = request.body.asJson.get
        val bookingRequest = jsValue.as[BookingRequest]
        val bookingResult = bookingService.bookTransport(bookingRequest)
        val js = bookingResult match{
          case Left(error) => createError(error)
          case Right(data) => createOk(Json.toJson(data))
        }
        Ok(js)

      }
  }

  //curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://www.localhost.com:9000/booking/{bookingId}
  def bookings(bookingId: String) = Action {

    val bookings = bookingService.bookingsForUser(bookingId)
    val result: JsObject = bookings match {
      case Right(data) => createOk(Json.toJson(data))
      case Left(error)   => createError(error)

    }
    Ok(result)

  }

 

}