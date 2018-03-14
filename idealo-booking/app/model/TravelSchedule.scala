package model

import java.util.Date

case class TravelSchedule(key:String,travelId:String, travelTimestamp:Date,seatAvailable:Int) extends BaseEntity[String]{
  def getKey:String = key
}