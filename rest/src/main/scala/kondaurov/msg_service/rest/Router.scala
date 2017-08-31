package kondaurov.msg_service.rest

import akka.http.scaladsl.server.Directives._

class Router() {

  def client = {

  }

  val route = pathEndOrSingleSlash {
    complete("rest my msg service")
  }

}
