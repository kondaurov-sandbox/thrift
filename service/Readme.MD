Проблемы:

1. Race conditions

2. Синхронизация link service хранилища с record service и tag service. 

Что если такой случай:

recordservice.add("tag a")

tagservice.link("tag a", "record 1")

tagservice.link

tagservice.getRecordsWithTag(Seq("tag a"))
