package graphics.opengl

import config
import ClientContext
import org.lwjgl.opengl.*
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.GL_MULTISAMPLE
import org.lwjgl.opengl.GL13.GL_TEXTURE_3D
import utils.LogLevel
import utils.XH_STATUS_OPENGL_ERROR
import utils.logger
import java.nio.IntBuffer

/** OpenGL Utils
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
object XHR_OpenGL {
    private lateinit var context: ClientContext

    var XHR_OPENGL_VERSION: String? = null
        private set

    var XHR_OPENGL_MAX_COLOR_ATTACHMENTS: Int = 0
        private set

    var modernGL = false
        private set

    var msaa = 0
        private set

    @JvmStatic
    fun init(pContext: ClientContext) {
        context = pContext

        setMSAA(context.window.config.msaa)

        XHR_OPENGL_VERSION = glGetString(GL_VERSION)
        XHR_OPENGL_MAX_COLOR_ATTACHMENTS = glGetInteger(GL30.GL_MAX_COLOR_ATTACHMENTS)

        modernGL = setStandardOpenGLVersion()

        glEnable(GL_TEXTURE_2D)
        if (context.projection == "3d")
            glEnable(GL_TEXTURE_3D)

        logger().internal_log("VSync: ${context.window.config.vSync}", LogLevel.CONFIG, "GLUtils")
        logger().internal_log("Modern OpenGL Support: $modernGL ($XHR_OPENGL_VERSION)", LogLevel.CONFIG, "GLUtils")
        logger().internal_log("Texture enabled", LogLevel.CONFIG, "GLUtils")
        logger().internal_log("Max Color Attachments $XHR_OPENGL_MAX_COLOR_ATTACHMENTS", LogLevel.FINE, "GLUtils")
    }

    fun setupDebugCallback(caps: GLCapabilities) {
        GLUtil.setupDebugMessageCallback()

        if (caps.OpenGL43)
            GL43.glDebugMessageControl(
                GL43.GL_DEBUG_SOURCE_API,
                GL43.GL_DEBUG_TYPE_OTHER,
                GL43.GL_DEBUG_SEVERITY_NOTIFICATION,
                IntBuffer.allocate(0),
                false
            )
        else if (caps.GL_KHR_debug) {
            KHRDebug.glDebugMessageControl(
                KHRDebug.GL_DEBUG_SOURCE_API,
                KHRDebug.GL_DEBUG_TYPE_OTHER,
                KHRDebug.GL_DEBUG_SEVERITY_NOTIFICATION,
                IntBuffer.allocate(0),
                false
            )
        } else if (caps.GL_ARB_debug_output) {
            ARBDebugOutput.glDebugMessageControlARB(
                ARBDebugOutput.GL_DEBUG_SOURCE_API_ARB,
                ARBDebugOutput.GL_DEBUG_TYPE_OTHER_ARB,
                ARBDebugOutput.GL_DEBUG_SEVERITY_LOW_ARB,
                IntBuffer.allocate(0),
                false
            )
        }
    }

    private fun setStandardOpenGLVersion(): Boolean {
        if (XHR_OPENGL_VERSION == null) {
            logger().wLog("Can't find OpenGL version. It might impact the program.")
            return false
        }

        val glVersionString = XHR_OPENGL_VERSION!!.split(" ")[0].trim()
        val glVersion = glVersionString.substring(0, 3).toFloat()

        if (glVersion < config("gl.modernGLVersion").toString().toFloat())
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