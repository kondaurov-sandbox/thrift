package kondaurov.msg_service.service.utils

import com.github.kondaurovdev.snippets.text.HashHelper

sealed trait IdGeneratorIface {
  def getNextId: Either[String, String]
}

object IdGenerator {

  import kondaurov.msg_service.service.Snippets

  def apply() = new IdGenerator(Snippets.HashHelper)

}

class IdGenerator(val hashHelper: HashHelper) extends IdGeneratorIface {

  import java.time.LocalDateTime

  def getNextId: Either[String, String] = {
    hashHelper.getMd5(LocalDateTime.now.toString)
  }

}

object IdGeneratorInt {

  def apply(startFrom: Int): IdGeneratorInt = new IdGeneratorInt(startFrom)

}

class IdGeneratorInt(startFrom: Int) extends IdGeneratorIface {

  private var current = startFrom

  def getNextId: Either[String, String] = {
    val result = current.toString
    current += 1
    Right(result)
  }

}

