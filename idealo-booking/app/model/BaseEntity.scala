package model

trait BaseEntity[K] {
  def getKey():K 
}