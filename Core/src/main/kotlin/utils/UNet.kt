/** System Utilities
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
package utils

import java.awt.Desktop
import java.net.InetAddress
import java.net.URI
import java.net.URISyntaxException
import java.net.URL

/**
 * Open a webpage on the default browser of the device.
 * @throws URISyntaxException
 */
fun xh_open_webpage(url: URL): Boolean {
    try {
        return xh_open_webpage(url.toURI())
    } catch (e: URISyntaxException) {
        logger().throwException("Cannot open webpage: ${url.toURI().path}", e,
            classSource = "UNet", statusCode = XH_STATUS_CONNECTION_ERROR)
    }
}

/**
 * Open a webpage on the default browser of the device.
 * @throws Exception
 */
fun xh_open_webpage(uri: URI): Boolean {
    val desktop = if (Desktop.isDesktopSupported()) Desktop.getDesktop() else null
    if (desktop != null && desktop.isSupported((Desktop.Action.BROWSE))) {
        try {
            desktop.browse(uri)
            return true
        } catch (e: Exception) {
            logger().throwException("Cannot open webpage: ${uri.path}", e,
                classSource = "UNet", statusCode = XH_STATUS_CONNECTION_ERROR)
        }
    }

    return false
}

/**
 * Execute a ping test to a specified ip address.
 */
@JvmOverloads
fun xh_ping_test(address: String, timeout: Int = 10000): Float {
    val time = XH_Timer()

    xh_tryCatch {
        val ip = InetAddress.getByName(address)
        val reachable = ip.isReachable(timeout)
    }

    logger().internal_log("IP $address reachable.", LogLevel.FINER, "UNet")

    return time.elapsed / 1_000_000.0f
}