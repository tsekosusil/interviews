package hbasePersistent

import org.apache.hadoop.hbase._
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.util._
import java.util.Properties
import java.lang.reflect.ParameterizedType;
import annotation._
import scala.collection.JavaConversions.seqAsJavaList

trait BasePersistence[T] {
  implicit val intToByteArray: Int => Array[Byte] = data => Bytes.toBytes(data)
  implicit val strToByteArray: String => Array[Byte] = data => Bytes.toBytes(data)
  implicit val doubleToByteArray: Double => Array[Byte] = data => Bytes.toBytes(data)
  implicit val longToByteArray: Long => Array[Byte] = data => Bytes.toBytes(data)

  implicit val byteArrayToInt: Array[Byte] => Int = array => Bytes.toInt(array)
  implicit val byteArrayToDouble: Array[Byte] => Double = array => Bytes.toDouble(array)
  implicit val byteArrayToString: Array[Byte] => String = array => Bytes.toString(array)
  implicit val byteArrayToLong: Array[Byte] => Long = array => Bytes.toLong(array)

  val objectToHbase: T => Put = createStorageConversion()
  val hbaseToObject: Result => T = createRetrivalConversion()

  def save(t: T) {

  }
  def save(data: List[T]) {

  }
  def get(get: Get): T
  def get(key: String): T
  def scan(scan: Scan): List[T]
  def scan(keyStart: String, keyEnd: String): List[T]

  protected def createStorageConversion(): T => Put
  protected def createRetrivalConversion(): Result => T

}

abstract class AbstractPersistence[T](tableName: String)(implicit connection: Connection) extends BasePersistence[T] {

  override def get(get: Get): T = {
    val table = connection.getTable(TableName.valueOf(tableName))
    val result = table.get(get)
    table.close()
    //    conversion.apply(result)
    hbaseToObject.apply(result)
  }
  override def get(key: String): T = {
    get(new Get(Bytes.toBytes(key)))
  }

  override def save(t: T) {
    val tn = TableName.valueOf(this.tableName)
    println(tn)
    val table = connection.getTable(tn)
    println(table)
    table.put(objectToHbase(t))
    table.close()
  }

  override def save(data: List[T]) {
    val table = connection.getTable(TableName.valueOf(tableName))
    table.put(data.map(objectToHbase))
    table.close()
  }

  override def scan(scan: Scan): List[T] = {
    val table = connection.getTable(TableName.valueOf(tableName))
    val resultSet = table.getScanner(scan)
    val listBuffer: scala.collection.mutable.ListBuffer[T] = scala.collection.mutable.ListBuffer()
    var result: Result = null
    while ((result = resultSet.next()) != null) {
      listBuffer.+=:(hbaseToObject.apply(result))
    }
    table.close()
    listBuffer.toList

  }

  override def scan(keyStart: String, keyEnd: String): List[T] = {
    val valScan = new Scan(keyStart, keyEnd)
    this.scan(valScan)
  }

}