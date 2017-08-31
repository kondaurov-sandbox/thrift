package kondaurov.msg_service.service.link

import com.twitter.util.Future
import kondaurov.msg_service.thrift.{AppService, Record, Tag}

class LinkServiceThriftImpl(linkService: LinkService) extends AppService.FutureIface {

  import scala.language.implicitConversions

  implicit def either2Future[R](e: Either[String, R]): Future[R] = {
    e.fold(
      err => Future.exception(throw new Exception(err)),
      id => Future.value(id)
    )
  }

  /**
    * Создать запись
    *
    * @return - сгенерированный UUID новой записи
    * @param title
    **/
  def insertRecord(title: String): Future[String] = {
    linkService.recordStorage.add(title)
  }

  /**
    * Создать тег
    *
    * @return - сгенерированный UUID нового тега
    * @param title
    *
    **/
  def insertTag(name: String): Future[String] = {
    linkService.tagStorage.add(name)
  }

  /**
    * Вернуть список записей к которым присоединены теги
    *
    * ?Игнорируем несуществующие id тегов
    *
    * @return Список записей
    **/
  def getRecordsByTagIds(tagIds: collection.Set[String]): Future[Seq[Record]] = {
    Future.value(linkService.getRecordsByTagIds(tagIds))
  }

  /**
    * Вернуть список тегов которые принадлежат одной записи
    *
    * ?Игнорируем несуществующие recordId
    **/
  def getTagsByRecordId(recordId: String): Future[Seq[Tag]] = {
    Future.value(linkService.getTagsByRecordId(recordId))
  }

  /**
    * Связать запись с тегом
    *
    * @throws , Exception если запись или тег не существуют
    **/
  def link(recordId: String, tagId: String): Future[Unit] = {
    linkService.link(recordId, tagId)
  }

  /**
    * Удалить связь записи с тегом
    *
    * @throw Exception если связь не найдена
    **/
  def unlink(recordId: String, tagId: String): Future[Unit] = {

    linkService.unlink(recordId, tagId)

  }

}
