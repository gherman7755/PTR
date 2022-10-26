package main

import akka.actor.{Actor, ActorPath, ActorRef, OneForOneStrategy, PoisonPill, Props, SupervisorStrategy}
import akka.http.scaladsl.model.sse.ServerSentEvent
import main.Main.{manager, system}
import Protocol.manage

import scala.collection.mutable.ListBuffer
import java.util.UUID

class WorkerSupervisor extends Actor {
  var actorPool: ListBuffer[ActorPath] = new ListBuffer[ActorPath]

  override def receive: Receive = {
    case listOfEvents: ListBuffer[ServerSentEvent] => {
      var needActors = (listOfEvents.length.toFloat / 2.0f).round
      println(needActors + " - has to be total")
      // Killing workers
      if (actorPool.length >= needActors){
        var actorsToKill = actorPool.slice(needActors-1, actorPool.length)
        println(actorsToKill.length + " - has to be killed")
        actorPool = actorPool.slice(0, needActors)
        for(worker <- actorsToKill){
          system.actorSelection(worker) ! PoisonPill
        }
        println("Killed workers: " + actorsToKill.length)
        println("Remained workers: " + actorPool.length)
      }
      // Creating workers
      else {
        var actorsToCreate = needActors - actorPool.length
        for(_ <- 0 until actorsToCreate){
          actorPool += system.actorOf(Props(classOf[UsualActor]), UUID.randomUUID().toString).path
        }
        println("Created workers: " + actorsToCreate)
        println("Total workers: " + actorPool.length)
      }

      manager ! manage(actorPool, listOfEvents)
      Thread.sleep(20)
    }
  }

  override val supervisorStrategy: OneForOneStrategy = OneForOneStrategy() {
    case _ => SupervisorStrategy.Restart
  }
}
