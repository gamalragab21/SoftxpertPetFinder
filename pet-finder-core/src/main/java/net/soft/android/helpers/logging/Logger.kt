package net.soft.android.helpers.logging

class Logger(private val clazz: Class<*>) {
    fun debug(message: String?) {
        LoggerFactory.currentLogWriter.debug(clazz, message)
    }

    fun info(message: String?) {
        LoggerFactory.currentLogWriter.info(clazz, message)
    }

    fun warning(message: String?) {
        LoggerFactory.currentLogWriter.warning(clazz, message)
    }

    /**
     * Log an exception (throwable) at the ERROR level with an
     * accompanying message.
     *
     * @param message the message accompanying the exception
     * @param throwable   the exception (throwable) to log
     */
    fun error(message: String?, throwable: Throwable? = null) {
        LoggerFactory.currentLogWriter.error(clazz, message, throwable)
    }
}