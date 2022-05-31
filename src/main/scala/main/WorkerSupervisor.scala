package main

import akka.actor.{Actor, ActorPath, ActorRef, OneForOneStrategy, Props, SupervisorStrategy}
import akka.http.scaladsl.model.sse.ServerSentEvent
import main.Main.{manager, system}
import Protocol.manage

import scala.collection.mutable.ListBuffer
import java.util.UUID

class WorkerSupervisor extends Actor {
  var actorPool: ListBuffer[ActorPath] = new ListBuffer[ActorPath]

  override def receive: Receive = {
    case listOfEvents: ListBuffer[ServerSentEvent] => {
      for(i <- 0 to listOfEvents.length - 1){
        actorPool += system.actorOf(Props(classOf[UsualActor]), UUID.randomUUID().toString).path
      }
      manager ! manage(actorPool, listOfEvents)
      Thread.sleep(20)
      actorPool.clear()
    }
  }

  override val supervisorStrategy: OneForOneStrategy = OneForOneStrategy() {
    case _ => SupervisorStrategy.Stop
  }
}
