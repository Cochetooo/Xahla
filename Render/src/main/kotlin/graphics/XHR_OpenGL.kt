package graphics

import XHR_OPENGL_MAX_COLOR_ATTACHMENTS
import XHR_OPENGL_VERSION
import config
import context.XHR_ClientContext
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.GL_MULTISAMPLE
import org.lwjgl.opengl.GL13.GL_TEXTURE_3D
import utils.XH_LogLevel
import utils.logger

object XHR_OpenGL {
    private lateinit var context: XHR_ClientContext

    val modernGL = setStandardOpenGLVersion()
    var msaa = 0
        private set

    @JvmStatic
    fun init(pContext: XHR_ClientContext) {
        context = pContext

        setMSAA(context.window.config.msaa)

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
        val glVersionString = XHR_OPENGL_VERSION.split(" ")[0].trim()
        val glVersion = glVersionString.substring(0, 3).toFloat()

        if (glVersion < config()["rendering.standard_opengl_version"] as Float)
            return false
        return true
    }

    @JvmStatic
    fun setMSAA(level: Int) {
        if (level < 0 || level > 3)
            logger().throwException("MSAA level must be between 0 and 3", IllegalArgumentException())

        if (level == 0)
            glDisable(GL_MULTISAMPLE)
        else
            glEnable(GL_MULTISAMPLE)

        msaa = level
    }
}

fun gl() = XHR_OpenGL