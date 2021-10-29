/** App Info & Constants
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
package utils

import java.util.*

val XH_FRAMEWORK_VERSION_MAJOR = 1
val XH_FRAMEWORK_VERSION_MINOR = 0
val XH_FRAMEWORK_VERSION_FIX = 0

val XH_FRAMEWORK_VERSION = "$XH_FRAMEWORK_VERSION_MAJOR.$XH_FRAMEWORK_VERSION_MINOR.$XH_FRAMEWORK_VERSION_FIX"

val XH_CORE_VERSION = "0.3.0"

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
val XH_STATUS_GLFW_ERROR = 12

val XH_CONTEXT_INFO = mapOf(
    "OS Name" to System.getProperty("os.name"),
    "OS Architecture" to System.getProperty("os.arch"),
    "OS Version" to System.getProperty("os.version"),
    "Java Home" to System.getProperty("java.home"),
    "Java Version" to System.getProperty("java.version"),
    "JRE Vendor Name" to System.getProperty("java.vendor"),
    "JRE Vendor URL" to System.getProperty("java.vendor.url"),
    "User Directory" to System.getProperty("user.dir"),
    "User Home" to System.getProperty("user.home"),
    "User Name" to System.getProperty("user.name"),
    "Locale Language" to XH_LOCALE_LANG,
    "Xahla Framework Version" to XH_FRAMEWORK_VERSION,
    "Xahla Environment" to XH_FRAMEWORK_ENVIRONMENT
)

val XH_CONFIG_CLASS = "app.class"
val XH_CONFIG_DEFAULT_CLASS = "XH_App"

val XH_CONFIG_UPS = "app.update_per_second"
val XH_CONFIG_DEFAULT_UPS = 50