/** Extensions for Java/Kotlin standard features
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
package utils

/**
 * Automated try catch that throws the possible caught exception with the [XH_Logger.throwException] method.
 */
fun xh_tryCatch(expression: () -> Unit) = xh_tryCatch(expression, null)

/**
 * Automated try catch that throws the possible caught exception with the [XH_Logger.throwException] method.
 */
@JvmName("tryCatch")
@JvmOverloads
fun xh_tryCatch(expression: () -> Unit, message: String? = null, catchException: Class<out Exception> = Exception::class.java,
    statusCode: Int = XH_STATUS_GENERAL_ERROR) {
    try {
        expression()
    } catch (e: Exception) {
        if (catchException.isAssignableFrom(e.javaClass)) {
            logger().throwException(message, e,
                classSource = "XH_UStd", statusCode = XH_STATUS_GENERAL_ERROR)
        }
    }
}