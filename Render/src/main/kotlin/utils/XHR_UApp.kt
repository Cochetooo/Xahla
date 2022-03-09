/*
    CONFIGURATIONS
 */

val XHR_CONFIG_FPS = "app.frame_per_second"
val XHR_CONFIG_DEFAULT_FPS = 144

val XHR_CONFIG_PROJECTION = "rendering.projection"
val XHR_CONFIG_DEFAULT_PROJECTION = "2d"

val XHR_CONFIG_ENGINE = "rendering.engine"
val XHR_CONFIG_DEFAULT_ENGINE = "graphics/opengl"

val XHR_CONFIG_STANDARD_OPENGL_VERSION = "rendering.standard_opengl_version"
val XHR_CONFIG_DEFAULT_STANDARD_OPENGL_VERSION = 4.0

val XHR_CONFIG_INITIAL_WIDTH = "rendering.initial_width"
val XHR_CONFIG_DEFAULT_INITIAL_WIDTH = 1280

val XHR_CONFIG_INITIAL_HEIGHT = "rendering.initial_height"
val XHR_CONFIG_DEFAULT_INITIAL_HEIGHT = 720

val XHR_CONFIG_RESIZABLE = "rendering.resizable"
val XHR_CONFIG_DEFAULT_RESIZABLE = true

val XHR_CONFIG_FULLSCREEN = "rendering.fullscreen"
val XHR_CONFIG_DEFAULT_FULLSCREEN = false

val XHR_CONFIG_WINDOW_TITLE = "rendering.window_title"
val XHR_CONFIG_DEFAULT_WINDOW_TITLE = "My First Xahla App"

val XHR_CONFIG_COLOR_BUFFER_BITS = "rendering.color_buffer_bits"
val XHR_CONFIG_DEFAULT_COLOR_BUFFER_BITS = 32

val XHR_CONFIG_FLOATING = "rendering.floating"
val XHR_CONFIG_DEFAULT_FLOATING = true

val XHR_CONFIG_DECORATION = "rendering.decoration"
val XHR_CONFIG_DEFAULT_DECORATION = true

val XHR_CONFIG_MSAA = "rendering.msaa"
val XHR_CONFIG_DEFAULT_MSAA = 0

val XHR_CONFIG_CENTER_CURSOR = "rendering.center_cursor"
val XHR_CONFIG_DEFAULT_CENTER_CURSOR = true

val XHR_CONFIG_VSYNC = "rendering.vsync"
val XHR_CONFIG_DEFAULT_VSYNC = false

val XHR_CONFIG_GL_ATTRIB_LOCATION_POSITION = "gl.attrib_location_position"
val XHR_CONFIG_GL_DEFAULT_ATTRIB_LOCATION_POSITION = "in_position"

val XHR_CONFIG_GL_ATTRIB_LOCATION_COLOR = "gl.attrib_location_color"
val XHR_CONFIG_GL_DEFAULT_ATTRIB_LOCATION_COLOR = "in_color"

val XHR_CONFIG_GL_ATTRIB_LOCATION_NORMAL = "gl.attrib_location_normal"
val XHR_CONFIG_GL_DEFAULT_ATTRIB_LOCATION_NORMAL = "in_normal"

val XHR_CONFIG_GL_ATTRIB_LOCATION_TEXCOORD = "gl.attrib_location_texcoord"
val XHR_CONFIG_GL_DEFAULT_ATTRIB_LOCATION_TEXCOORD = "in_texCoord"

val XHR_CONFIG_GL_GEOMETRY_EXT = "gl.geometry_shader_ext"
val XHR_CONFIG_GL_DEFAULT_GEOMETRY_EXT = ".gsh"

val XHR_CONFIG_GL_VERTEX_EXT = "gl.vertex_shader_ext"
val XHR_CONFIG_GL_DEFAULT_VERTEX_EXT = ".vsh"

val XHR_CONFIG_GL_FRAGMENT_EXT = "gl.fragment_shader_ext"
val XHR_CONFIG_GL_DEFAULT_FRAGMENT_EXT = ".fsh"

/*  Other Utils  */

val XHR_OPENGL = "graphics/opengl"
val XHR_VULKAN = "vulkan"
val XHR_NOENGINE = "none"

val XHR_VERSION = "0.2.0"

val XHR_ENGINE = config()[XHR_CONFIG_ENGINE] as String

val XHR_GL_ATTRIB_LOCATION_POSITION = config()[XHR_CONFIG_GL_ATTRIB_LOCATION_POSITION] as String
val XHR_GL_ATTRIB_LOCATION_COLOR = config()[XHR_CONFIG_GL_ATTRIB_LOCATION_COLOR] as String
val XHR_GL_ATTRIB_LOCATION_NORMAL = config()[XHR_CONFIG_GL_ATTRIB_LOCATION_NORMAL] as String
val XHR_GL_ATTRIB_LOCATION_TEXCOORD = config()[XHR_CONFIG_GL_ATTRIB_LOCATION_TEXCOORD] as String