package net.xpert.android.helpers.logging.writers

import net.xpert.android.helpers.logging.LogWriter

class ConsoleWriter(private val appName: String, override val isDebugEnabled: Boolean) :
    LogWriter {

    override fun debug(clazz: Class<*>, message: String?) {
        if (isDebugEnabled)
            println(String.format("$appName D: [%s] %s", clazz.simpleName, message))
    }

    override fun info(clazz: Class<*>, message: String?) {
        if (isDebugEnabled)
            println(String.format("$appName I: [%s] %s", clazz.simpleName, message))
    }

    override fun warning(clazz: Class<*>, message: String?) {
        if (isDebugEnabled)
            println(String.format("$appName W: [%s] %s", clazz.simpleName, message))
    }

    override fun error(clazz: Class<*>, message: String?, throwable: Throwable?) {
        if (isDebugEnabled) {
            val fullMessage = if (throwable == null)
                String.format("$appName E: [%s] %s", clazz.simpleName, message)
            else
                String.format(
                    "$appName E: [%s] %s %s", clazz.simpleName, message, throwable.toString()
                )

            System.err.println(fullMessage)
        }
    }
}