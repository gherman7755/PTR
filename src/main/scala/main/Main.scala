package main
import akka.actor.{ActorSystem, Props}
import java.util.UUID

object Main extends App {
  implicit val system = ActorSystem()

  val chainer = system.actorOf(Props(classOf[Chainer]))
  val supervisor = system.actorOf(Props(classOf[WorkerSupervisor]))
  val manager = system.actorOf(Props(classOf[Manager]))

}
