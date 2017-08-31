package service

import org.specs2.mutable.Specification
import kondaurov.msg_service.service.link.LinkService
import kondaurov.msg_service.thrift.Tag

class LinkServiceSpec extends Specification with iLinkServiceContext {


  "LinkService tests" should {

    "get tag by id" in { service: LinkService =>

      service.tagStorage.getById("1") must beRight(Tag("1", "tag 1"))

    }

    "link tag" in { service: LinkService =>

      service.link("1", "3") must beRight

      service.getTagsByRecordId("1") shouldEqual Seq(Tag("3", "tag 3"))

    }

    "link -> unlink tag" in { service: LinkService =>

      service.link("1", "3") must beRight

      service.getTagsByRecordId("1") shouldEqual List(Tag("3", "tag 3"))

      service.unlink("1", "3") must beRight

      service.getTagsByRecordId("1") shouldEqual List()

    }

    "get records by tag ids" in { service: LinkService =>

      service.link("1", "3") must beRight

      service.link("1", "2") must beRight

      service.getTagsByRecordId("1") shouldEqual List(Tag("3", "tag 3"), Tag("2", "tag 2"))

      service.unlink("1", "3") must beRight

      service.getTagsByRecordId("1") shouldEqual List(Tag("2", "tag 2"))

    }

  }

}
