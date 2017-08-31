package kondaurov.msg_service.service.utils

import java.util.concurrent.locks.{ReadWriteLock, ReentrantReadWriteLock}

class ReadWriteLocker {

  private val lock: ReadWriteLock = new ReentrantReadWriteLock()

  def runInsideWrite[R](f: => R): R = {
    lock.writeLock().lock()
    try {
      f
    } finally {
      lock.writeLock().unlock()
    }
  }

  def runInsideRead[R](f: => R): R = {
    lock.readLock().lock()
    try {
      f
    } finally {
      lock.readLock().unlock()
    }
  }

}