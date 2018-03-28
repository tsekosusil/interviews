package jobs

import model._
import service._
import java.util.UUID
import hbasePersistent._

object HBaseTest {
  def main(args: Array[String]) {
    val messageService = implicitly[MessageService]
    println(messageService)

    val uuid = UUID.randomUUID()
    val message = messageService.generateRandomMessage(uuid)
    val repository: MessageRepository = implicitly[MessageRepository]
    println(message)
    repository.save(message)
  }
}