import akka.actor._
import akka.stream._
import akka.stream.scaladsl._
import scala.concurrent.ExecutionContext

import stream._

case object Start

class MyActor extends Actor {
  //implicit val sys: ActorSystem = context.system
  implicit val mat: Materializer = Materializer(context)
  implicit val ec: ExecutionContext = context.dispatcher

  override def receive: Receive = {
    case Start =>
      
      println(s"will start on ${Thread.currentThread().getName()}")

      Source(List(1, 2, 3)).map { i =>
        println(s"got $i on ${Thread.currentThread().getName()}")
      }.to(Sink.foreach { i =>
        println(s"got $i on ${Thread.currentThread().getName()}")
      }).spawn()
  }
}

object App {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
  
    val actor = system.actorOf(Props(new MyActor).withDispatcher("my-dispatcher"))
  
    actor ! Start
  }
}
