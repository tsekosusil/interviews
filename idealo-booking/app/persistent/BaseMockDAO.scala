package persistent

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map

trait BaseMockDAO[T] {

  //TODO : change implementation to map. 
  private val data = new ListBuffer[T]
  private val newData = Map[String,T]()

  def create(t: T): T = {
    data += t
    t
  }

  def delete(t: T): T = {
    (data -= t)
    t
  }

  def findOne(predicate: T => Boolean): Option[T] = {
   data.find(predicate)
  }
  
  def findAll():List[T] = data.toList
  
  def filter(predicate:T => Boolean):List[T] = data.filter(predicate).toList
  
  //TODO map makes deletion and update fast!
  def update(t:T) = ???
  
}