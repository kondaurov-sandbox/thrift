package kondaurov.msg_service.service.storage

import java.util.concurrent.locks.ReentrantReadWriteLock

import kondaurov.msg_service.service.utils.{IdGenerator, IdGeneratorInt, ReadWriteLocker}
import kondaurov.msg_service.thrift.Record

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class RecordStorage() extends ReadWriteLocker {

  private val storage = mutable.Map.empty[String, Record]

  private val lock = new ReentrantReadWriteLock()

  private val idGenerator = IdGeneratorInt(1)

  def getById(id: String): Either[String, Record] = {
    runInsideRead(storage.get(id).toRight(s"Can't find record with id '$id'"))
  }

  def add(title: String): Either[String, String] = {
    runInsideWrite {
      for {
        id <- idGenerator.getNextId
        result <- {
          getById(id).fold(
            _ => {
              storage(id) = Record(id, title)
              Right(id)
            },
            _ => Left("Not unique record id")
          )
        }
      } yield result
    }
  }

  def getByIds(ids: Iterable[String]): Seq[Record] = {
    lock.readLock().lock()
    runInsideRead {
      val result = ListBuffer.empty[Record]
      ids.foreach(id => {
        storage.get(id).foreach(v => result += v)
      })
      result.result()
    }
  }

}
