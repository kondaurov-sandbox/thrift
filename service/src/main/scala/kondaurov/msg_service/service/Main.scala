package kondaurov.msg_service.service

import com.twitter.finagle.{ListeningServer, Thrift}
import com.twitter.scrooge.ThriftService
import kondaurov.msg_service.service.link.{LinkService, LinkServiceThriftImpl}
import kondaurov.msg_service.service.storage.{RecordStorage, TagStorage}
import play.api.libs.json.JsValue

object Main extends App {

  def addAndBindService(addr: String, iface: ThriftService): Either[JsValue, ListeningServer] = {
    PlayJson.Try.tryBlock({
      Thrift.server.serveIface(addr, iface)
    }, s"Can't bind ${iface.getClass.getSimpleName} to $addr").left.map(err => {
      err
    })
  }

  val linkServiceThriftService = new LinkServiceThriftImpl(new LinkService(new RecordStorage(), new TagStorage()))

  for {
    server <- addAndBindService("0.0.0.0:1234", linkServiceThriftService)
  } yield {
    println("service started. press to exit...")

    scala.io.StdIn.readLine()

    println("shutting down...")
    server.close().foreach(_ => println("success"))
  }

}
