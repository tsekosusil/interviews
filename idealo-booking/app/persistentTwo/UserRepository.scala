package persistentTwo

import model.User

class UserRepository extends BaseMockRepository[String, User]

object UserRepository {
  implicit val userRepository = new UserRepository()
}