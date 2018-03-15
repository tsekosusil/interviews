

import org.junit.Test

import model._
import persistent._
import persistentTwo._
class DaoTest {

  @Test
  def testDao {
    val userDao: UserRepository = new UserRepository();
    val bookingDao = new BookingRepository();
    val travelDao = new TravelDAO();
    val travelScheduleDao = new TravelScheduleDAO();

    val user1 = User("1", "user1@gmail.com", false)
    val user2 = User("2", "user2@gmail.com", false)
    val user3 = User("3", "user3@gmail.com", false)
    var user4 = User("2", "user4@gmail.com", false)

    userDao.create(user1)
    userDao.create(user2)
    userDao.create(user3)

    println(userDao.findAll)

    userDao.delete(user3)

    println(userDao.findAll)

    println(userDao.findByKey("1"))

    println(userDao.findOne(user => user.email == "user2@gmail.com"))
    
    userDao.update(user4)
    
    println(userDao.findAll)

  }
}