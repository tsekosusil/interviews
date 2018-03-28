package model
import java.util.UUID
import java.text.SimpleDateFormat
import java.util.Date
import java.time._

case class Data(deviceId: UUID, location: Location, temperature: Int, time: Long) {
  val prettyDate: String = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time))

}
case class Location(latitude: Double, longitude: Double) {}
