package kondaurov.msg_service.service.storage

import kondaurov.msg_service.service.utils.{IdGeneratorInt, ReadWriteLocker}
import kondaurov.msg_service.thrift.Tag

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class TagStorage() extends ReadWriteLocker {

  private val storage = mutable.Map.empty[String, Tag]

  private val idGenerator = IdGeneratorInt(1)

  def getById(id: String): Either[String, Tag] = {
    runInsideRead(storage.get(id)).toRight(s"Can't find tag with id '$id'")
  }

  def add(name: String): Either[String, String] = {

    runInsideWrite {
      for {
        id <- idGenerator.getNextId
        result <- {
          getById(id).fold(
            _ => {
              storage(id) = Tag(id, name)
              Right(id)
            },
            _ => Left("Not unique tag id")
          )
        }
      } yield result
    }

  }

  def getByIds(ids: Iterable[String]): Seq[Tag] = {
    runInsideRead {
      val result = ListBuffer.empty[Tag]
      ids.foreach(id => {
        storage.get(id).foreach(v => result += v)
      })
      result.result()
    }
  }

}
