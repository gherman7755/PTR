package main
import akka.actor.{Actor, ActorPath, ActorRef, PoisonPill}
import akka.http.scaladsl.model.sse.ServerSentEvent
import Protocol.manage
import akka.pattern.gracefulStop

import scala.collection.mutable.ListBuffer
import main.Main.system

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

class Manager extends Actor {
  override def receive: Receive = {
    case manage(pool: ListBuffer[ActorPath], tweets: ListBuffer[ServerSentEvent]) =>
        if (pool.nonEmpty && tweets.nonEmpty){
            for(i <- tweets.indices){
                system.actorSelection(pool(i % pool.length)) ! tweets(i)
          }
            for(i <- pool.indices){
                system.actorSelection(pool(i)) ! PoisonPill
            }
        }
  }
}
