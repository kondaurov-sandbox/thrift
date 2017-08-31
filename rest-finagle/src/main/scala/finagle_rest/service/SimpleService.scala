package finagle_rest.service

import com.twitter.finagle.http.Response
import com.twitter.finagle.{Service, http}
import com.twitter.util.Future

class SimpleService extends Service[http.Request, http.Response] {

  def helloResponse: Response = {
    val resp = http.Response(http.Status.Ok)
    resp.setContentString("\"Hello from finagle!\"")
    resp.setContentTypeJson()
    resp
  }

  def unknownPathResponse: Response = {
    val resp = http.Response(http.Status.BadRequest)
    resp.setContentString("\"Unknown path\"")
    resp.setContentTypeJson()
    resp
  }

  def apply(req: http.Request): Future[http.Response] = {

    val resp = req.path match {
      case "/" => helloResponse
      case "" => helloResponse
      case _ => unknownPathResponse

    }

    Future.value { resp }

  }

}
