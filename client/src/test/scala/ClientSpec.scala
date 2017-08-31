import com.twitter.util.Await
import org.specs2.mutable.Specification

class ClientSpec extends Specification with iClientContext {

  "Msg Client" should {

    "get msgs" in { context: FinagledClientContext =>

      Await.result {
        for {
          list <- context.client.getRecords()
        } yield list.length >= 0
      } must beTrue

    }

  }

}
