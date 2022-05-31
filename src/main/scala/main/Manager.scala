package main
import akka.actor.{Actor, ActorRef}
import akka.http.scaladsl.model.sse.ServerSentEvent

import scala.collection.mutable.ListBuffer

class Manager extends Actor {
  var pool: ListBuffer[ActorRef] = new ListBuffer[ActorRef]
  var count: Int = 0
  override def receive: Receive = {
    case tweet: ListBuffer[ServerSentEvent] => {
      var index: Int = count

      do
      {
        index = (index + 1) % pool.length
        pool(index) ! tweet(index)
        count = index
      } while(index != count)

      count = 0
    }
    case actorPool: ListBuffer[ActorRef] => {
      pool = actorPool
    }
    case _ =>
  }
}
