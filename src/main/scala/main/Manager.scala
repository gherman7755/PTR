package main
import akka.actor.{Actor, ActorRef, ActorPath}
import akka.http.scaladsl.model.sse.ServerSentEvent
import Protocol.manage
import scala.collection.mutable.ListBuffer
import main.Main.system

class Manager extends Actor {
  var count: Int = 0
  override def receive: Receive = {
    case manage(pool: ListBuffer[ActorPath], tweet: ListBuffer[ServerSentEvent]) => {
      if (pool.nonEmpty && tweet.nonEmpty){
        for(i <- 0 until pool.length - 1){
          system.actorSelection(pool(i)) ! tweet(i)
        }
      }
    }
  }
}
