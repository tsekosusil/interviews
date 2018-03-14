package model

import java.util.Date;
case class Booking(bookingId:String,user:User,travel:Travel,travelTimestamp:Date,bookingTimestamp:Date,noOfSeat:Int) extends BaseEntity[String]{
  override def getKey:String = bookingId
}

