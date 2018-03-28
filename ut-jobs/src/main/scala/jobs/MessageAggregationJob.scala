package jobs

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.StreamingContext;
import org.apache.spark.SparkContext
import util.Utils
import org.apache.spark.streaming.kafka.KafkaUtils
import kafka.serializer.StringDecoder
import hbasePersistent.MessageRepository
import model._
import kafka._
import org.apache.kafka.clients.producer.KafkaProducer
import service.JsonSerializationService._
import hbasePersistent.MessageAggregationRepository
import service._

object MessageAggregationJob {
  def main(args:Array[String]){
       println("Hello from spark streaming")
    val conf = new SparkConf().setMaster("local[2]").setAppName(
      "MessageStoreJob");
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Durations.seconds(2))
    val prop = Utils.getProperty
    val topics = Set(prop.getProperty("aggregationMessage.kafkaTopic"))
    println(topics)
    val kafkaParams = Map[String, String]("metadata.broker.list" -> prop.getProperty("bootstrap.servers"))

// hbase set up
//    val messageAggregationRepository: MessageAggregationRepository = implicitly[MessageAggregationRepository]
    val messageService:MessageService = implicitly[MessageService]

    //
    val json = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topics)
    //dequeue from kafka
    val messages = json.map({ case (k, v) => service.JsonSerializationService.dailyAggregationDeserializer.apply(v) })

    //parse the request and calculate the aggregation
    messages.foreachRDD(rdd => rdd.foreachPartition(iterator => iterator.foreach(messageService.calculateAggregationMessage(_))))
    messages.print()

    ssc.start()
    ssc.awaitTermination()

  }
}