package persistentTwo

import model.User
import model.TravelSchedule

class TravelScheduleRepository extends BaseMockRepository[String,TravelSchedule] 

object TravelScheduleRepository{
  implicit val repository = new TravelScheduleRepository()
}