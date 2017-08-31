package kondaurov.msg_service.rest

import com.twitter.finagle.Thrift
import com.twitter.finagle.thrift.ThriftClientRequest
import play.api.libs.json.JsValue

object FinagleClient {

  type finagleThriftService = com.twitter.finagle.Service[ThriftClientRequest, Array[Byte]]

  def getService(addr: String): Either[JsValue, finagleThriftService] = {
    PlayJson.Try.tryBlock({
      Thrift.newClient(addr).toService
    }, s"Can't get service for '$addr'")
  }

}
