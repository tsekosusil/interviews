package model
import java.util.Date
import java.time._
case class DailyAggregationOrder(uuidStr: String, strDay: String, timestampStart: Long, timestampEnd: Long) {
  def identifier = uuidStr + "|" + strDay
}
object DailyAggregationOrder {
  private def getStartAndEndOfDayInMilis(date: Date): (Long, Long) = {
    val localTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val todayMidnight = localTime.atStartOfDay()
    val tommorowMidnight = todayMidnight.plusDays(1)

    val x = Date.from(todayMidnight.atZone(ZoneId.systemDefault()).toInstant()).getTime
    val y = Date.from(tommorowMidnight.atZone(ZoneId.systemDefault()).toInstant()).getTime
    (x, y)
  }

  implicit def createAggregationOrderFromMessage(message: Message): DailyAggregationOrder = {
    val (milisStart, milisEnd) = getStartAndEndOfDayInMilis(new Date(message.data.time))
    val result = DailyAggregationOrder(message.data.deviceId.toString(), message.data.prettyDate, milisStart, milisEnd)
    result
  }
}