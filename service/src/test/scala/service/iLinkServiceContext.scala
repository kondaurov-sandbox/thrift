package service

import kondaurov.msg_service.service.link.LinkService
import kondaurov.msg_service.service.storage.{RecordStorage, TagStorage}
import org.specs2.execute.AsResult
import org.specs2.specification.ForEach

trait iLinkServiceContext extends ForEach[LinkService]  {

  def foreach[R](f: (LinkService) => R)(implicit evidence$3: AsResult[R]) = {

    val service = new LinkService(new RecordStorage(), new TagStorage())

    println("Creating context scope...")

    service.tagStorage.add("tag 1")
    service.tagStorage.add("tag 2")
    service.tagStorage.add("tag 3")

    service.recordStorage.add("record 1")
    service.recordStorage.add("record 2")
    service.recordStorage.add("record 3")

    AsResult(f(service))

  }

}
