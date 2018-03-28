package kafka

import org.apache.kafka.clients.consumer._
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties
import java.util.Arrays

//deprecated use spark streaming and kafka integration
class KafkaReceiver[V, T](val topic: String)(implicit kafkaConsumer: KafkaConsumer[String, String], deserializer: V => T) {

  kafkaConsumer.subscribe(Arrays.asList(topic));
  
  def pull: Option[T] = {
    val record = kafkaConsumer.poll(5000)
    Option.empty
  }
}

object KafkaReceiver {
  implicit def getKafkaConsumer(prop: Properties): KafkaConsumer[String, String] = new KafkaConsumer(prop)

}