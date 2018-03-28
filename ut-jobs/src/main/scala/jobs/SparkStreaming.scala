package jobs

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.StreamingContext;
import org.apache.spark.SparkContext
import util.Utils
import org.apache.spark.streaming.kafka.KafkaUtils
import kafka.serializer.StringDecoder
import model._
import kafka._
import org.apache.kafka.clients.producer.KafkaProducer
import service.JsonSerializationService._
import service._
object SparkStreaming {
  def main(args: Array[String]) {

    //source https://github.com/eBay/Spark/blob/master/examples/src/main/java/org/apache/spark/examples/streaming/JavaDirectKafkaWordCount.java
    println("Hello from spark streaming")
    val conf = new SparkConf().setMaster("local[2]").setAppName(
      "MessageStoreJob");
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Durations.seconds(2))
    val prop = Utils.getProperty
    val topics = Set(prop.getProperty("sensor.kafkaTopic"))
    println(topics)
    val kafkaParams = Map[String, String]("metadata.broker.list" -> prop.getProperty("bootstrap.servers"))


    val messageService:MessageService = implicitly[MessageService]
    //
    val json = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topics)
    val messages = json.map({ case (k, v) => service.JsonSerializationService.messageDeserializer.apply(v) })

    messages.foreachRDD(rdd => rdd.foreachPartition(messageService.storeMessageBulk(_)))

    implicit val kafkaProducer = new KafkaProducer[String, String](prop)
    val topic = prop.getProperty("aggregationMessage.kafkaTopic")
    implicit val kafkaSender = new KafkaSender[DailyAggregationOrder, String](topic)
    implicit val kafkaConsumer: DailyAggregationOrder => Unit = msg => kafkaSender.send(msg)

    val aggregationMessage = messages.map(DailyAggregationOrder.createAggregationOrderFromMessage)
    aggregationMessage.foreachRDD(rdd => rdd.foreach(kafkaSender.send(_)))

    messages.print()

    ssc.start()
    ssc.awaitTermination()

  }
}


