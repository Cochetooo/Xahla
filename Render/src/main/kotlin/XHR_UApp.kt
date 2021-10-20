import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL30.GL_MAX_COLOR_ATTACHMENTS

val XHR_OPENGL = "opengl"
val XHR_VULKAN = "vulkan"
val XHR_NOENGINE = "none"

val XHR_ENGINE = config()["rendering.engine"] as String

val XHR_OPENGL_VERSION = glGetString(GL_VERSION)
val XHR_OPENGL_MAX_COLOR_ATTACHMENTS = glGetInteger(GL_MAX_COLOR_ATTACHMENTS)

val XHR_VERSION = "0.2.0"