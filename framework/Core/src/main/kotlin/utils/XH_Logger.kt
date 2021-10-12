/** Exception Logging
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
package utils

import org.jsoup.Jsoup
import java.io.File
import java.net.URL
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

enum class XH_LogLevel {
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
    var logLevel: XH_LogLevel = XH_LogLevel.CONFIG
    var printer = System.out

    val html_content = File("templates/stacktrace.html").bufferedReader().use { it.readText() }

    fun eLog(message: String? = null, logFile: Boolean = false, classSource: Class<Any>? = null)
        = log(message, XH_LogLevel.SEVERE, logFile, classSource)
    fun wLog(message: String? = null, logFile: Boolean = false, classSource: Class<Any>? = null)
        = log(message, XH_LogLevel.WARNING, logFile, classSource)

    fun log(message: String? = null, logLevel: XH_LogLevel = XH_LogLevel.INFO, logFile: Boolean = false, classSource: Class<Any>? = null) {
        if (logLevel > this.logLevel)
            return

        printer.println(message)
    }

    fun throwException(message: String? = null, exception: Exception = Exception(), logFile: Boolean = false, classSource: Class<Any>? = null, statusCode: Int = XH_STATUS_GENERAL_ERROR): Nothing {
        log("""
            ###### AN ERROR HAS OCCURED #####
            # Exception type: ${exception.javaClass.simpleName}
            # Exception message: ${exception.localizedMessage}
            # Exit Status Code: $statusCode
            # Additional message: ${message ?: "None"}
            # From: ${classSource?.simpleName ?: "Unknown Class"}
            # Occured during: ${SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").format(Date())}
        """.trimIndent())

        if (logFile) {
            val document = Jsoup.parse(html_content)

            val fileClass = document.select("#file_class")
            fileClass.html("${classSource?.simpleName ?: "Unknown Class"}")

            val exceptionName = document.select("#exception_name")
            exceptionName.html("${exception.javaClass.simpleName}")

            val exceptionMessage = document.select("#exception_message")
            exceptionMessage.html("${exception.localizedMessage}")

            val additionalMessage = document.select("#additional_message")
            additionalMessage.html("${message ?: "None"}")

            val filename = "logs/xh_err_log-${SimpleDateFormat("dd_MM_yyyy-HH_mm_ss").format(Date())}.html"
            File(filename).writeText(document.html())
            openWebpage(URL("file:///${Paths.get("").toAbsolutePath()}/$filename"))
        }

        exitProcess(statusCode)
    }
}