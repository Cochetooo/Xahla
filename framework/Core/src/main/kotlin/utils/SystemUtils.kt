/** System Utilities
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
package utils

import java.awt.Desktop
import java.net.URI
import java.net.URISyntaxException
import java.net.URL
import java.util.*
import kotlin.system.exitProcess

enum class XH_EOperatingSystem {
    WINDOWS, UNIX, OSX, ANDROID, IOS
}

fun openWebpage(url: URL): Boolean {
    try {
        return openWebpage(url.toURI())
    } catch (e: URISyntaxException) {
        XH_Logger.throwException(exception = e)
    }

    return false
}

fun openWebpage(uri: URI): Boolean {
    val desktop = if (Desktop.isDesktopSupported()) Desktop.getDesktop() else null
    if (desktop != null && desktop.isSupported((Desktop.Action.BROWSE))) {
        try {
            desktop.browse(uri)
            return true
        } catch (e: Exception) {
            XH_Logger.throwException(exception = e)
        }
    }

    return false
}

val XH_DEVICE_OS: XH_EOperatingSystem = with(System.getProperty("os.name").lowercase()) {
    when {
        contains("win") -> XH_EOperatingSystem.WINDOWS
        contains("lin") or contains("uni") -> XH_EOperatingSystem.UNIX
        contains("mac") or contains("osx") -> XH_EOperatingSystem.OSX
        // Miss Android & iOS
        else -> XH_Logger.throwException("Unsupported Operating System.", statusCode = XH_STATUS_GENERAL_ERROR)
    }
}

val XH_FRAMEWORK_VERSION_MAJOR = 1
val XH_FRAMEWORK_VERSION_MINOR = 0
val XH_FRAMEWORK_VERSION_FIX = 0

val XH_FRAMEWORK_VERSION = "$XH_FRAMEWORK_VERSION_MAJOR.$XH_FRAMEWORK_VERSION_MINOR.$XH_FRAMEWORK_VERSION_FIX"

val XH_FRAMEWORK_ENVIRONMENT = "Dev"
val XH_FRAMEWORK_ROOT = ""

val XH_LOCALE_LANG = Locale.getDefault().language

val XH_STATUS_GENERAL_ERROR = 1
val XH_STATUS_ENGINE_ERROR = 2
val XH_STATUS_OPENGL_ERROR = 3
val XH_STATUS_VULKAN_ERROR = 4
val XH_STATUS_OPENAL_ERROR = 5
val XH_STATUS_OPENVR_ERROR = 6
val XH_STATUS_SERVER_ERROR = 7
val XH_STATUS_CONNECTION_ERROR = 8
val XH_STATUS_DB_ERROR = 9
val XH_STATUS_JSON_ERROR = 10
val XH_STATUS_JOML_ERROR = 11