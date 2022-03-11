package graphics.opengl

import config
import org.lwjgl.opengl.GL15.GL_STATIC_DRAW
import org.lwjgl.opengl.GL30.*
import org.lwjgl.system.MemoryStack.stackPush
import utils.XH_LogLevel
import utils.logger
import java.nio.FloatBuffer

class GLVertexBufferObject(private val shader: GLShader, private val pos: Int = 0, private val color: Int = 0, private val normal: Int = 0,
                           private val texCoord: Int = 0, private val drawMode: Int, private val nbVertices: Int, data: FloatArray) {

    val vao = glGenVertexArrays()
    val vbo = glGenBuffers()
    private var rowSize = pos + color + normal + texCoord
    private var bufferSize = data.size

    var buffer: FloatBuffer
        private set

    private val positionLocation = shader.getAttribLocation(config("gl.attribLocationPosition") as String)
    private val colorLocation = shader.getAttribLocation(config("gl.attribLocationColor") as String)
    private val normalLocation = shader.getAttribLocation(config("gl.attribLocationNormal") as String)
    private val texCoordLocation = shader.getAttribLocation(config("gl.attribLocationTexCoord") as String)

    init {
        logger().internal_log("Buffer size: $bufferSize", logLevel = XH_LogLevel.FINEST, classSource = "XHR_GLVertexBufferObject")
        logger().internal_log("Nb. Vertices: $nbVertices", logLevel = XH_LogLevel.FINEST, classSource = "XHR_GLVertexBufferObject")
        logger().internal_log("Row size: $rowSize", logLevel = XH_LogLevel.FINEST, classSource = "XHR_GLVertexBufferObject")
        logger().internal_log("Nb of objects: ${(bufferSize / (nbVertices * rowSize))}", logLevel = XH_LogLevel.FINEST, classSource = "XHR_GLVertexBufferObject")

        stackPush().apply {
            buffer = this.floats(*data)
            update(GL_STATIC_DRAW)
        }
    }

    fun update(drawMode: Int) {
        glBindVertexArray(vao)

        if (pos > 0)
            glEnableVertexAttribArray(positionLocation)

        if (color > 0)
            glEnableVertexAttribArray(colorLocation)

        if (normal > 0)
            glEnableVertexAttribArray(normalLocation)

        if (texCoord > 0)
            glEnableVertexAttribArray(texCoordLocation)

        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, buffer, drawMode)

        var size = 0L

        if (pos > 0) {
            glVertexAttribPointer(positionLocation, pos, GL_FLOAT, false, rowSize * nbVertices, 0)
            size += pos
        }

        if (color > 0) {
            glVertexAttribPointer(colorLocation, color, GL_FLOAT, false, rowSize * nbVertices, size * 4)
            size += color
        }

        if (normal > 0) {
            glVertexAttribPointer(normalLocation, normal, GL_FLOAT, false, rowSize * nbVertices, size * 4)
            size += normal
        }

        if (color > 0) {
            glVertexAttribPointer(texCoordLocation, texCoord, GL_FLOAT, false, rowSize * nbVertices, size * 4)
            size += texCoord
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindVertexArray(0)

        buffer.clear()

        logger().internal_log("VAO updated (pos=$positionLocation color=$colorLocation normal=$normalLocation texCoord=$texCoordLocation", XH_LogLevel.FINEST, "XHR_GLVertexBufferObject")
    }

    fun subData(data: FloatArray) {
        this.bufferSize = data.size
        buffer.clear()

        stackPush().apply {
            buffer = this.floats(*data)
        }

        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferSubData(GL_ARRAY_BUFFER, 0, buffer)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    fun render() {
        glBindVertexArray(vao)
        glDrawArrays(GL_QUADS, 0, bufferSize / rowSize)
        glBindVertexArray(0)
    }

    fun dispose() {
        glDeleteVertexArrays(vao)
        glDeleteBuffers(vbo)

        if (pos > 0)
            glDisableVertexAttribArray(positionLocation)
        if (color > 0)
            glDisableVertexAttribArray(colorLocation)
        if (normal > 0)
            glDisableVertexAttribArray(normalLocation)
        if (texCoord > 0)
            glDisableVertexAttribArray(texCoordLocation)
    }

}