package net.soft.android.helpers.logging

import net.soft.android.helpers.logging.writers.DummyWriter

object LoggerFactory {
    var currentLogWriter: LogWriter = DummyWriter()
        private set

    /**
     * @param clazz the returned logger will be named after clazz
     * @return logger
     */
    fun getLogger(clazz: Class<*>): Logger {
        return Logger(clazz)
    }

    fun setLogWriter(currentLogWriter: LogWriter) {
        LoggerFactory.currentLogWriter = currentLogWriter
    }
}

inline fun <reified T> T.getClassLogger(): Logger {
    if (T::class.isCompanion) {
        return LoggerFactory.getLogger(T::class.java.enclosingClass as Class<*>)
    }
    return LoggerFactory.getLogger(T::class.java)
}