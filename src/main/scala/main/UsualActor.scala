package main

import akka.actor.Actor
import akka.http.scaladsl.model.sse.ServerSentEvent
import main.Main.system
import scala.util.Random

class UsualActor extends Actor {
  override def receive: Receive = {
    case tweet: ServerSentEvent =>
      Thread.sleep(Random.nextInt(450) + 50)
      val data: String = tweet.getData()
      if (data.contains(": panic")){
        println("Corrupted Message")
      }
      else{
        println("Message: " + data)
      }
      system.stop(self)
  }
}
