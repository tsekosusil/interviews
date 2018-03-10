package persistent

import model.User

class UserDAO extends BaseMockDAO[User] {

  override def create(user: User): List[User] = {
    val newId: String = System.currentTimeMillis() + "-" + user.email;
    super.create(User(newId, user.email, false))
  }
}

object UserDAO {
  def populateMock(dao: UserDAO) {
    dao.create(User("1", "user1@gmail.com", false));
    dao.create(User("2", "user1@gmail.com", false));

  }
}