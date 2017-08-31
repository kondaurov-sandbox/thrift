import com.twitter.finagle.Thrift
import kondaurov.msg_service.thrift.link.LinkService
import org.specs2.execute.{AsResult, Result}
import org.specs2.specification.ForEach

class FinagledClientContext(
                           val client: LinkService.FinagledClient)

trait iClientContext extends ForEach[FinagledClientContext] {

  def foreach[R](f: (FinagledClientContext) => R)(implicit evidence$3: AsResult[R]): Result = {

    val factory = Thrift.newClient("localhost:1234")

    val client = new LinkService.FinagledClient(factory.toService)

    try {
      println(s"open connection: ${factory.hashCode()}")
      AsResult(f(new FinagledClientContext(client)))
    } finally factory.close().foreach(_ => println(s"close connection ${factory.hashCode()}"))

  }

}
