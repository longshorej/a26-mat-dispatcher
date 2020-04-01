import akka.actor._
import akka.stream._
import scala.concurrent.ExecutionContext

case object Start

class MyChildActor extends Actor {
  override def receive: Receive = {
    case Start =>
      println(s"MyChildActor received Start on ${Thread.currentThread().getName}")
  }
}

class MyActor extends Actor {
  implicit val mat: Materializer = Materializer(context)
  implicit val ec: ExecutionContext = context.dispatcher

  override def receive: Receive = {
    case Start =>
      println(s"MyActor received Start on ${Thread.currentThread().getName}")

      context.actorOf(Props(new MyChildActor)) ! Start
  }
}

object App {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem()
  
    val actor = system.actorOf(Props(new MyActor).withDispatcher("my-dispatcher"))
  
    actor ! Start
  }
}
