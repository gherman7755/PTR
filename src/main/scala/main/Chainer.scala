package main

import akka.actor.{Actor, ActorRef, Cancellable}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.sse.ServerSentEvent
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, Uri}
import akka.stream.alpakka.sse.scaladsl.EventSource
import akka.stream.alpakka.sse.scaladsl.EventSource.EventSource
import main.Main.{manager, supervisor, system}
import org.json4s.jackson.JsonMethods
import org.json4s.jackson.JsonMethods.pretty

import scala.concurrent.Future
import JsonMethods._
import akka.Done
import system.dispatcher

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class Chainer extends Actor {
  var number: Int = 0
  val send: HttpRequest => Future[HttpResponse] = Http().singleRequest(_)
  var listOfEvents : ListBuffer[ServerSentEvent] = new ListBuffer[ServerSentEvent]

  val eventSource: EventSource = EventSource(
    Uri("http://localhost:4000/tweets/1"),
    send
  )

  val eventSource2: EventSource = EventSource(
    Uri("http://localhost:4000/tweets/2"),
    send
  )

  val events: Future[Done] = eventSource.runForeach(filtering)
  val events2: Future[Done] = eventSource2.runForeach(filtering)


  override def receive: Receive = {
    case _ =>
  }

  val count: Cancellable = system.scheduler.schedule(0 seconds, 1000 milliseconds) {
    //println(number)
    supervisor ! listOfEvents
    Thread.sleep(20)
    listOfEvents.clear()
  }

  def filtering(event: ServerSentEvent): Unit =
    {
      listOfEvents += event
      //println(pretty(render(parse(event.data))))
    }
}
