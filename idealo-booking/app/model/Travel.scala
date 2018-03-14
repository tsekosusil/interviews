package model

case class Travel(travelCode:String, departurePoint:String,  arrivalPoint:String) extends BaseEntity[String]{
  def getKey:String=travelCode
}