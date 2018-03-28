package hbasePersistent

import model._
import org.apache.hadoop.hbase._
import org.apache.hadoop.hbase.client._

import org.apache.hadoop.hbase.util._
import java.util.Properties
import java.lang.reflect.ParameterizedType;
import annotation._
import scala.collection.JavaConversions.seqAsJavaList
import java.util.UUID

class MessageAggregationRepositoryMock(tableName: String)(implicit connection: Connection) extends MessageAggregationRepository(tableName)(connection){
  
  //97b0d530-f308-49ef-89af-a1ef92659d86
  //1cad683c-e1ad-4335-8a30-0d144b1e3671
  val data:Map[String,Message] = Map("97b0d530-f308-49ef-89af-a1ef92659d86|2018-03-14" -> null,
      "97b0d530-f308-49ef-89af-a1ef92659d86|2018-03-15" -> null
  
  )
}