package main

import akka.actor.ActorPath
import akka.http.scaladsl.model.sse.ServerSentEvent
import scala.collection.mutable.ListBuffer

object Protocol {
    case class manage(pool: ListBuffer[ActorPath], tweet: ListBuffer[ServerSentEvent])
}
