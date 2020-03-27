package stream

import akka.dispatch.Dispatcher
import akka.stream.{ActorAttributes, Materializer}
import akka.stream.scaladsl.RunnableGraph
import scala.concurrent.ExecutionContext

class StreamExt[A](graph: RunnableGraph[A])(implicit ec: ExecutionContext, mat: Materializer) {
  def spawn(): A = ec match {
    case dispatcher: Dispatcher =>
      graph
        .withAttributes(ActorAttributes.dispatcher(dispatcher.id))
        .run()

    case _ =>
      graph.run()
  }
}

