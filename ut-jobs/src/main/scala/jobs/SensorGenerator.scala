package jobs

import service._
import java.util.Properties
import java.util.UUID
import model._
import java.util.function._
import jobs._
import kafka._
import service.JsonSerializationService._
import util._
import org.apache.kafka.clients.producer.KafkaProducer
import service._

object SensorGenerator {

  def main(arg: Array[String]) {
    val prop = Utils.getProperty
    implicit val kafkaProducer = new KafkaProducer[String, String](prop)
    val topic = prop.getProperty("sensor.kafkaTopic")
    implicit val kafkaSender = new KafkaSender[Message, String](topic)
    implicit val kafkaConsumer: Message => Unit = msg => kafkaSender.send(msg)
    val messageService = implicitly[MessageService]
    val list = List[UUID](UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())
    while (true) {
      list.foreach(uuid => {
        kafkaConsumer(messageService.generateRandomMessage(uuid))
      })

      Thread.sleep(1000)
    }

  }

}