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

val XH_DEVICE_OS: XH_EOperatingSystem = with(System.getProperty("os.name").lowercase()) {
    when {
        contains("win") -> XH_EOperatingSystem.WINDOWS
        contains("lin") or contains("uni") -> XH_EOperatingSystem.UNIX
        contains("mac") or contains("osx") -> XH_EOperatingSystem.OSX
        // Miss Android & iOS
        else -> XH_Logger.throwException("Unsupported Operating System.", statusCode = XH_STATUS_GENERAL_ERROR)
    }
}

class XH_Timer {
    private var current = System.nanoTime()
    var elapsed = 0L
        private set

    fun stop() {
        val old = current
        current = System.nanoTime()
        elapsed = current - old
    }

    fun elapsedNow(): Long = System.nanoTime() - current

    fun reset() {
        current = System.nanoTime()
    }
}