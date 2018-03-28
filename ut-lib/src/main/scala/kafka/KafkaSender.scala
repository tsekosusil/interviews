package kafka

import java.util.Properties
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

class KafkaSender[T,V](val topic: String)(implicit producer: KafkaProducer[String, V], serializer: T => V) {
  //TODO to refactor, since it's thread safe it's safe to share among many sender
//  val producer = new KafkaProducer[String, String](prop)

  def send(t: T) {
    val value = serializer.apply(t);
    val data = new ProducerRecord[String, V](topic, value);
    println(value)
    producer.send(data)
  }

}