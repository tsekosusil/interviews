package persistentTwo

import model.BaseEntity

trait BaseMockRepository[K, T <: BaseEntity[K]] {
  private val data = scala.collection.mutable.Map[K, T]()

  def create(t: T): T = {
    data += (t.getKey() -> t)
    t
  }

  def delete(t: T): T = {
    data -= t.getKey()
    t
  }

  def findOne(predicate: T => Boolean): Option[T] = data.values.find(predicate)

  def findByKey(key: K): Option[T] = Option(data(key))

  def filter(predicate: T => Boolean): List[T] = data.values.filter(predicate).toList

  def update(t: T) = data(t.getKey()) = t

  //useful only for debug/test while in memory mode  
  def findAll = data.values.toList
}