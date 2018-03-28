package jobs

import service.MessageService
import java.util._
import java.util.function.Consumer
import model._

class MessageGenerator(uuid: UUID, running: Boolean)(implicit messageService: MessageService, consumer:Message => Unit) extends Runnable {

  override def run() {
    while (running) {
      consumer.apply(messageService.generateRandomMessage(uuid))
      Thread.sleep(1000)

    }
  }
}