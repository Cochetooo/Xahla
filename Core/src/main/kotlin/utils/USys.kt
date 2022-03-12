/** System Utilities
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
package utils

enum class XH_EOperatingSystem {
    WINDOWS, UNIX, OSX, ANDROID, IOS
}

val XH_DEVICE_OS: XH_EOperatingSystem = with(System.getProperty("os.name").lowercase()) {
    when {
        contains("win") -> XH_EOperatingSystem.WINDOWS
        contains("lin") or contains("uni") -> XH_EOperatingSystem.UNIX
        contains("mac") or contains("osx") -> XH_EOperatingSystem.OSX
        // Miss Android & iOS
        else -> logger().throwException("Unsupported Operating System.", classSource = "USys",
            statusCode = XH_STATUS_GENERAL_ERROR)
    }
}

/**
 * Get the program current memory usage
 */
fun getMemoryUsage(): Long = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024

/**
 * Get the memory capacity of the device
 */
fun getMemoryCapacity(): Long = Runtime.getRuntime().maxMemory()

/**
 * Get the number of available logical processors.
 */
fun getAvailableProcessors(): Int = Runtime.getRuntime().availableProcessors()

class XH_Timer {
    private var current = System.nanoTime()
    var elapsed = 0L
        private set
        get() = System.nanoTime() - current

    fun reset() {
        current = System.nanoTime()
    }
}