package main

import akka.actor.Actor
import akka.http.scaladsl.model.sse.ServerSentEvent

class UsualActor extends Actor {
  override def receive: Receive = {
    case tweet: ServerSentEvent => {
      println(self.path.name)
    }
  }
}
