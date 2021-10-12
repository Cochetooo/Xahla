package utils

import kotlin.system.exitProcess

enum class XH_LogLevel( {
    OFF,
    SEVERE,
    WARNING,
    INFO,
    CONFIG,
    FINE,
    FINER,
    FINEST,
    ALL
}

object XH_ExceptionHandler {
    var logLevel: XH_LogLevel = XH_LogLevel.CONFIG
    var printer = System.out

    fun eLog(exception: Exception, message: String? = null, logFile: Boolean = false, classSource: Class<Any>? = null, statusCode: Int = XH_STATUS_FATAL_ERROR)
        = log(exception, message, XH_LogLevel.SEVERE, logFile, classSource, statusCode)
    fun wLog(exception: Exception, message: String? = null, logFile: Boolean = false, classSource: Class<Any>? = null, statusCode: Int = XH_STATUS_FATAL_ERROR)
        = log(exception, message, XH_LogLevel.WARNING, logFile, classSource, statusCode)

    fun log(exception: Exception, message: String? = null, logLevel: XH_LogLevel = XH_LogLevel.INFO, logFile: Boolean = false, classSource: Class<Any>? = null, statusCode: Int = XH_STATUS_FATAL_ERROR) {
        if (logLevel > this.logLevel)
            return

        printer.println("""
            ###### AN ERROR HAS OCCURED #####
            # Exception type: ${exception.javaClass.simpleName}
            # Exception message: ${exception.localizedMessage}
            # Additional message ${message ?: "None"}
            # From: ${classSource?.simpleName ?: "Unknown Class"}
        """.trimIndent())

        exitProcess(statusCode)
    }
}