namespace * kondaurov.msg_service.thrift

typedef string UUID

struct Tag {
  1: required UUID id
  2: required string name
}

struct Record {
  1: required UUID id
  2: required string title
}

service AppService {

  /**
  * Создать запись
  *
  * @return - сгенерированный UUID новой записи
  * @param title - заголовок записи
  * @throw exception - если не удается создать запись
  **/
  UUID insertRecord(1: string title)

  /**
  * Создать тег
  *
  * @return - сгенерированный UUID нового тега
  * @param name - имя тега
  * @throw exception - если не удается создать тег
  **/
  UUID insertTag(1: string name)

  /**
  * Вернуть список записей к которым присоединены теги
  *
  * ?Игнорируем несуществующие id тегов
  *
  * @return Список записей
  **/
  list<Record> getRecordsByTagIds(1: set<string> tagIds)


  /**
  * Вернуть список тегов которые принадлежат одной записи
  *
  * ?Игнорируем несуществующие recordId
  **/
  list<Tag> getTagsByRecordId(1: string recordId)

  /**
  * Связать запись с тегом
  *
  * @throws, Exception если запись или тег не существуют
  **/
  void link(1: string recordId, 2: string tagId)

  /**
  * Удалить связь записи с тегом
  *
  * @throw Exception если связь не найдена
  **/
  void unlink(1: string recordId, 2: string tagId)

}