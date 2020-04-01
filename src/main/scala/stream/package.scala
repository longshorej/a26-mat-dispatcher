import akka.stream.Materializer
import akka.stream.scaladsl.RunnableGraph
import scala.concurrent.ExecutionContext

package object stream {
  implicit def enrichRunnableGraph[A](graph: RunnableGraph[A])(implicit ec: ExecutionContext, mat: Materializer): RunnableGraphExt[A] =
    new RunnableGraphExt(graph)
}
