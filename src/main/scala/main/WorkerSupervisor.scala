package main

import akka.actor.{Actor, ActorRef, OneForOneStrategy, Props, SupervisorStrategy}
import akka.http.scaladsl.model.sse.ServerSentEvent
import main.Main.{manager, system}

import scala.collection.mutable.ListBuffer
import java.util.UUID

class WorkerSupervisor extends Actor {
  var actorPool: ListBuffer[ActorRef] = new ListBuffer[ActorRef]

  override def receive: Receive = {
    case listOfEvents: ListBuffer[ServerSentEvent] => {
      for (_ <- listOfEvents.indices) {
        actorPool += system.actorOf(Props(classOf[UsualActor]), UUID.randomUUID().toString)
      }
      manager ! actorPool
      manager ! listOfEvents
    }
  }

  override val supervisorStrategy: OneForOneStrategy = OneForOneStrategy() {
    case _ => SupervisorStrategy.Restart
  }
}
