package graphics

import XHR_CONFIG_STANDARD_OPENGL_VERSION
import config
import context.XHR_ClientContext
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.GL_MULTISAMPLE
import org.lwjgl.opengl.GL13.GL_TEXTURE_3D
import org.lwjgl.opengl.GL30
import utils.XH_LogLevel
import utils.XH_STATUS_OPENGL_ERROR
import utils.logger

/** OpenGL Utils
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
object XHR_OpenGL {
    private lateinit var context: XHR_ClientContext

    var XHR_OPENGL_VERSION: String? = null
        private set

    var XHR_OPENGL_MAX_COLOR_ATTACHMENTS: Int = 0
        private set

    var modernGL = false
        private set

    var msaa = 0
        private set

    @JvmStatic
    fun init(pContext: XHR_ClientContext) {
        context = pContext

        setMSAA(context.window.config.msaa)

        XHR_OPENGL_VERSION = glGetString(GL_VERSION)
        XHR_OPENGL_MAX_COLOR_ATTACHMENTS = glGetInteger(GL30.GL_MAX_COLOR_ATTACHMENTS)

        setStandardOpenGLVersion()

        glEnable(GL_TEXTURE_2D)
        if (context.projection == "3d")
            glEnable(GL_TEXTURE_3D)

        logger().internal_log("VSync: ${context.window.config.vSync}", XH_LogLevel.CONFIG, "XHR_OpenGL")
        logger().internal_log("Modern OpenGL Support: $modernGL ($XHR_OPENGL_VERSION)", XH_LogLevel.CONFIG, "XHR_OpenGL")
        logger().internal_log("Texture enabled", XH_LogLevel.CONFIG, "XHR_OpenGL")
        logger().internal_log("Max Color Attachments $XHR_OPENGL_MAX_COLOR_ATTACHMENTS", XH_LogLevel.FINE, "XHR_OpenGL")
    }

    private fun setStandardOpenGLVersion(): Boolean {
        if (XHR_OPENGL_VERSION == null) {
            logger().wLog("Can't find OpenGL version. It might impact the program.")
            return false
        }

        val glVersionString = XHR_OPENGL_VERSION!!.split(" ")[0].trim()
        val glVersion = glVersionString.substring(0, 3).toFloat()

        if (glVersion < config()[XHR_CONFIG_STANDARD_OPENGL_VERSION] as Float)
            return false
        return true
    }

    @JvmStatic
    fun setMSAA(level: Int) {
        if (level < 0 || level > 3)
            logger().throwException("MSAA level must be between 0 and 3", IllegalArgumentException(),
                classSource = "XHR_OpenGL", statusCode = XH_STATUS_OPENGL_ERROR)

        if (level == 0)
            glDisable(GL_MULTISAMPLE)
        else
            glEnable(GL_MULTISAMPLE)

        msaa = level
    }
}

fun gl() = XHR_OpenGL