/** Extensions for primitives objects
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
package utils

import org.json.JSONException

fun xh_tryCatch(expression: () -> Unit) = xh_tryCatch(expression, null)

fun xh_tryCatch(expression: () -> Unit, message: String? = null, catchException: Class<out Exception> = Exception::class.java) {
    try {
        expression()
    } catch (e: Exception) {
        if (catchException.isAssignableFrom(e.javaClass)) {
            XH_Logger.throwException(message, e, statusCode = XH_STATUS_GENERAL_ERROR, logFile = true)
        }
    }
}