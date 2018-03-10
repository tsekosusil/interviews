package payload

import java.util.Date

case class BookingRequest(email:String,travelCode:String,travelTimestamp:Date,noOfSeat:Int)