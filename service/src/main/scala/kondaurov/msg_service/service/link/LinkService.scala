package kondaurov.msg_service.service.link

import kondaurov.msg_service.service.storage.{RecordStorage, TagStorage}
import kondaurov.msg_service.service.utils.ReadWriteLocker
import kondaurov.msg_service.thrift._

import scala.collection.mutable.ListBuffer
import scala.collection.{Seq, mutable}

class LinkService(
                  val recordStorage: RecordStorage,
                  val tagStorage: TagStorage
                 ) extends ReadWriteLocker {

  //recordId -> tagId
  private val recordLinkStorage = mutable.Map.empty[String, mutable.Set[String]]
  //tagId -> recordId
  private val tagLinkStorage = mutable.Map.empty[String, mutable.Set[String]]

  def getTagsByRecordId(recordId: String): Seq[Tag] = {
    runInsideRead {
      tagStorage.getByIds(recordLinkStorage.getOrElse(recordId, List()))
    }
  }

  def getRecordsByTagIds(tagIds: Iterable[String]): Seq[Record] = {

    runInsideRead {

      recordStorage.runInsideRead {

        val recordIds = ListBuffer.empty[String]

        tagIds.foreach(id => {
          tagLinkStorage.get(id).foreach(recIds => {
            recordIds ++= recIds
          })
        })

        recordStorage.getByIds(recordIds)

      }

    }

  }

  def link(recordId: String, tagId: String): Either[String, Unit] = {
    runInsideWrite {
      tagStorage.runInsideRead {
        recordStorage.runInsideRead {
          for {
            _ <- tagStorage.getById(tagId)
            _ <- recordStorage.getById(recordId)
          } yield {
            recordLinkStorage.get(recordId) match {
              case None => recordLinkStorage(recordId) = mutable.Set(tagId)
              case Some(currentTags) => recordLinkStorage(recordId) = currentTags += tagId
            }
            tagLinkStorage.get(tagId) match {
              case None => tagLinkStorage(tagId) = mutable.Set(recordId)
              case Some(currentRecors) => tagLinkStorage(tagId) = currentRecors += recordId
            }
          }
        }
      }
    }
  }

  def unlink(recordId: String, tagId: String): Either[String, Unit] = {
    runInsideWrite {
      tagStorage.runInsideRead {
        recordStorage.runInsideRead {
          for {
            _ <- {
              recordLinkStorage.get(recordId) match {
                case None => Left(s"Record '$recordId' doesn't have any tags")
                case Some(currentTags) => Right(recordLinkStorage(recordId) = currentTags -= tagId)
              }
            }
            _ <- {
              tagLinkStorage.get(tagId) match {
                case None => Left(s"Tag '$tagId' doesn't have any records")
                case Some(currentRecors) => Right(tagLinkStorage(tagId) = currentRecors -= recordId)
              }
            }
          } yield ()
        }
      }
    }
  }

}
