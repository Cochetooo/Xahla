/** Exception Logging
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
package utils

import config
import org.jsoup.Jsoup
import java.io.PrintWriter
import java.io.StringWriter
import java.net.URL
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

enum class LogLevel {
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

object XH_Logger {
    var logLevel: LogLevel = LogLevel.CONFIG
    var printer = System.out
    var internalLog = true
    var logFile = false
    var prefix = true
    var excepts: Array<String> = arrayOf()

    private val html_content = xh_file_read("templates/stacktrace.html")

    fun onAwake() {
        internalLog = config("debugger.internalLogging") as Boolean
        logFile = config("debugger.logExceptionFile") as Boolean
        prefix = config("debugger.prefix") as Boolean
    }

    /**
     * Log a message as a severe message in System.err
     */
    @JvmOverloads
    @JvmStatic
    fun eLog(message: Any, classSource: String? = null) {
        val tmp = printer
        printer = System.err
        log(message, LogLevel.SEVERE, classSource)
        printer = tmp
    }

    /**
     * Log a message as a warning message.
     */
    @JvmOverloads
    @JvmStatic
    fun wLog(message: Any, classSource: String? = null)
        = log(message, LogLevel.WARNING, classSource)

    /**
     * Discouraged to use this method if used externally.
     * Log any kind of message into a defined printer if the internal logging is enabled.
     */
    @JvmOverloads
    @JvmStatic
    fun internal_log(message: Any, logLevel: LogLevel = LogLevel.CONFIG, classSource: String) {
        if (!internalLog)
            return

        log(message, logLevel, classSource)
    }

    /**
     * Log any kind of message into a defined printer (by default, the standard JVM console System.out)
     */
    @JvmOverloads
    @JvmStatic
    fun log(message: Any, logLevel: LogLevel = LogLevel.INFO, classSource: String? = null) {
        if (logLevel > this.logLevel)
            return

        if (classSource in excepts)
            return

        if (prefix)
            printer.print("[$logLevel] ")
        if (classSource != null)
            printer.print("<$classSource> ")

        printer.println(message)
    }

    /**
     * Framework's standard for exception throwing, generates a detailed log message and quit the program with a given status code.
     *
     * A HTML log file can be requested with [logFile] = true.
     */
    @JvmOverloads
    @JvmStatic
    fun throwException(message: String? = null, exception: Exception = Exception(), classSource: String = "", statusCode: Int = XH_STATUS_GENERAL_ERROR): Nothing {
        eLog("""
            ###### AN ERROR HAS OCCURED #####
            # Exception type: ${exception.javaClass.simpleName}
            # Exception message: ${exception.localizedMessage}
            # Exit Status Code: $statusCode
            # Additional message: ${message ?: "None"}
            # From: ${classSource.ifEmpty { "Unknown Class" }}
            # Occured during: ${SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").format(Date())}
        """.trimIndent())

        if (logFile) {
            val document = Jsoup.parse(html_content)

            val fileClass = document.select("#file_class")
            fileClass.html(classSource.ifEmpty { "Unknown Class" })

            val exceptionName = document.select("#exception_name, title")
            exceptionName.html(exception.javaClass.simpleName)

            val statusCodeElem = document.select("#status_code")
            statusCodeElem.html("Exit Status Code: $statusCode")

            val exceptionMessage = document.select("#exception_message")
            exceptionMessage.html(exception.localizedMessage)

            val additionalMessage = document.select("#additional_message")
            additionalMessage.html(message ?: "")

            val listStacktrace = document.select("#list-stacktrace")
            var stacktraceContent = getStackTrace(exception)
            if (stacktraceContent.contains("\n")) {
                stacktraceContent = stacktraceContent.substring(stacktraceContent.indexOf("\n") + 2)
                var stacktrace = stacktraceContent.split("at ")
                stacktrace = stacktrace.subList(1, stacktrace.size - 1)
                var i = 1
                for (stack in stacktrace) {
                    listStacktrace.append("<li class=\"list-group-item\"><small>$i.</small> $stack</li>")
                    i++
                }
            } else {
                listStacktrace.html("<li class=\"list-group-item\">$stacktraceContent</li>")
            }

            val listContext = document.select("#list-context")
            for (key in XH_CONTEXT_INFO.keys)
                listContext.append("<li class=\"list-group-item\"><b>$key</b>: ${XH_CONTEXT_INFO[key]}</li>")

            val filename = "logs/xh_err_log-${SimpleDateFormat("dd_MM_yyyy-HH_mm_ss").format(Date())}.html"
            xh_file_write(filename, document.html())
            internal_log("See $filename for more information.", LogLevel.INFO, "XH_Logger")
            xh_open_webpage(URL("file:///${Paths.get("").toAbsolutePath()}/$filename"))
        }

        exitProcess(statusCode)
    }

    /**
     * Get the exact stack trace of an exception as a String.
     */
    @JvmStatic
    fun getStackTrace(ex: Throwable): String {
        val sw = StringWriter()
        val pw = PrintWriter(sw, true)
        ex.printStackTrace(pw)
        return sw.buffer.toString()
    }
}

fun logger() = XH_Logger