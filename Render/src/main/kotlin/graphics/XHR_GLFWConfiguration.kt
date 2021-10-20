package graphics

import org.joml.Vector2i

data class XHR_GLFWConfiguration(val resizable: Int, val fullscreen: Int, val width: Int, val height: Int,
    val title: String, val colorBufferBits: Int, val floating: Int, val decoration: Int, val msaa: Int,
    val centerCursor: Int, val vSync: Int) {
    fun getSize() = Vector2i(width, height)
}
