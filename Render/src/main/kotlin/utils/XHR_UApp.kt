/*  Other Utils  */

val XHR_OPENGL = "graphics/opengl"
val XHR_VULKAN = "vulkan"
val XHR_NOENGINE = "none"

val XHR_VERSION = "0.2.0"

val XHR_ENGINE = config("window.engine") as String

val XHR_SCREEN_WIDTH = config("window.screenWidth") as Int
val XHR_SCREEN_HEIGHT = config("window.screenHeight") as Int

val XHR_GL_ATTRIB_LOCATION_POSITION = config("gl.attribLocationPosition") as String
val XHR_GL_ATTRIB_LOCATION_COLOR = config("gl.attribLocationColor") as String
val XHR_GL_ATTRIB_LOCATION_NORMAL = config("gl.attribLocationNormal") as String
val XHR_GL_ATTRIB_LOCATION_TEXCOORD = config("gl.attribLocationTexCoord") as String