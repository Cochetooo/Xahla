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

val XH_DEVICE_OS: XH_EOperatingSystem = with(System.getProperty("os.name")) {
    when {
        contains("win") -> XH_EOperatingSystem.WINDOWS
        contains("lin") or contains("uni") -> XH_EOperatingSystem.UNIX
        contains("mac") or contains("osx") -> XH_EOperatingSystem.OSX
        // Miss Android & iOS
        else -> throw BootstrapMethodError("Unsupported Operating System.")
    }
}

val XH_FRAMEWORK_VERSION_MAJOR = 1
val XH_FRAMEWORK_VERSION_MINOR = 0
val XH_FRAMEWORK_VERSION_FIX = 0

val XH_FRAMEWORK_VERSION = "$XH_FRAMEWORK_VERSION_MAJOR.$XH_FRAMEWORK_VERSION_MINOR.$XH_FRAMEWORK_VERSION_FIX"

val XH_FRAMEWORK_ENVIRONMENT = "Dev"
val XH_FRAMEWORK_ROOT = "/../."

val XH_STATUS_FATAL_ERROR = -1