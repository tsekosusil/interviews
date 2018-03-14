package model

case class User(userId:String,email:String,deleted:Boolean) extends BaseEntity[String]{
  override def getKey() = userId
}