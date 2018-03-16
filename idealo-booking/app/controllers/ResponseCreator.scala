package controllers
import play.api.libs.json._

trait ResponseCreator {
  
   def createError(message: String): JsObject = {
    val jsonobj = Json.obj(
      "status" -> "OK",
      "errorMessage" -> message)
    jsonobj
  }

  def createOk(jsValue: JsValue): JsObject = {
    val jsonobj = Json.obj(
      "status" -> "OK",
      "data" -> jsValue,
      "errorMessage" -> "")
    jsonobj

  }
}