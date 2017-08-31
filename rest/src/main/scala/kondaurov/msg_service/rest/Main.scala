package kondaurov.msg_service.rest

import com.github.kondaurovdev.akka_http.models.{HttpConfig, HttpServer}

object Main extends App {

  val server = new HttpServer(Snippets.Lang.Future)
  val routes = new Router()

  (for {
    binding <- server.bind(routes.route, HttpConfig(8080, "0.0.0.0", "0.0.0.0"))
  } yield {

    println("Http server started on port 8080")

    scala.io.StdIn.readLine()

    println("Shutting down...")

    implicit val ec = server.actorSystem.dispatcher

    (for {
      _ <- binding.unbind()
      _ <- server.actorSystem.terminate()
    } yield {
      println("Successful shutdown")
    }).failed.foreach(err => println("Can't shutdown system"))

  }).left.map(err => err)

}

//object Main2 extends App {
//
//  println("main2")
//
//}