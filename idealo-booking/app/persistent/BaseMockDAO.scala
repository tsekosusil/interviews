package persistent

import scala.collection.mutable.ListBuffer

trait BaseMockDAO[T] {

  //TODO : change implementation to map. 
  private val data = new ListBuffer[T]

  def create(t: T): T = {
    data += t
    t
  }

  def delete(t: T): List[T] = {
    (data -= t).toList
  }

  def findOne(predicate: T => Boolean): Option[T] = {
   data.find(predicate)
  }
  
  def findAll():List[T] = data.toList
  
  def filter(predicate:T => Boolean):List[T] = data.filter(predicate).toList
  
  //TODO map makes deletion and update fast!
  def update(t:T) = ???
  
}