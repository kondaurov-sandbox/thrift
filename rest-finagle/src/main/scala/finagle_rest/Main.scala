package finagle_rest

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http
import com.twitter.util.{Await, Future}

import service.SimpleService

object Main extends App {

  val service = new SimpleService()

  val server = Http.serve(":8080", service)

  println("Started on http server on 8080. Press any key to shutdown...")

  scala.io.StdIn.readLine()

  server.close().foreach(_ => {
    println("shutdown successful")
  })


}
