package util;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerExample {

	public static void main(String args[]) {
		Properties prop = Util.getProperties();

		Producer<String, String> producer = new KafkaProducer<String, String>(prop);
		String topic = Util.getProperty("sensor.kafkaTopic").get();
		
		System.out.println(topic);
		ProducerRecord<String, String> data = new ProducerRecord<String, String>(topic, "aha");
		producer.send(data);
		producer.close();

		System.out.println(Util.getProperty("property1"));
	}
}
