package model
import java.util.UUID

sealed trait AggregationType {
}
case object DAILY extends AggregationType
case object GLOBAL extends AggregationType

case class MessageAggregation(uuid: UUID, loc: Location, maxTemp: Int, minTemp: Int, noDataPoint: Int, lastUpdate: Long, aggregationType: AggregationType) {

  def aggregateMessage(data: Message): MessageAggregation = {
    val maxTemp = Math.max(this.maxTemp, data.data.temperature)
    val minTemp = Math.min(this.minTemp, data.data.temperature)
    val noDataPoint = this.noDataPoint + 1
    val lastUpdate = data.data.time
    val newAggregation = MessageAggregation(data.data.deviceId, data.data.location, maxTemp, minTemp, noDataPoint, lastUpdate, this.aggregationType)
    newAggregation
  }

}