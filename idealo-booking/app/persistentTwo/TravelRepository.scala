package persistentTwo

import model.Travel

class TravelRepository extends BaseMockRepository[String,Travel] 

object TravelRepository{
  implicit val travelRepo = new TravelRepository()
}