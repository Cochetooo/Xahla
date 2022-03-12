import org.joml.Vector2f
import java.awt.Toolkit

/*  Other Utils  */

val XHR_OPENGL = "opengl"
val XHR_VULKAN = "vulkan"
val XHR_NOENGINE = "none"

val XHR_VERSION = "0.2.0"

val XHR_SCREEN_DIMENSION: Vector2f
    get() {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        return Vector2f(screenSize.width.toFloat(), screenSize.height.toFloat())
    }