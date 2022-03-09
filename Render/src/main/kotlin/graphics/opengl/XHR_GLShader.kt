package graphics.opengl

import XHR_CONFIG_GL_FRAGMENT_EXT
import XHR_CONFIG_GL_GEOMETRY_EXT
import XHR_CONFIG_GL_VERTEX_EXT
import XH_Context
import config
import graphics.gl
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER
import org.lwjgl.system.MemoryStack.stackPush
import utils.XH_STATUS_OPENGL_ERROR
import utils.logger
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

/**
 * Shader class allows GLSL interpretation and execution within the program.<br>
 *
 * @author Cochetooo
 * @version 1.6
 */
class XHR_GLShader(private val context: XH_Context, private val path: String, private val geometryShader: Boolean = false) {

    private val program: Int = glCreateProgram()

    init {
        if (program == GL_FALSE)
            logger().throwException("Can't create program for shader: $path", RuntimeException(), classSource = "XHR_GLShader", statusCode = XH_STATUS_OPENGL_ERROR)

        if (geometryShader)
            createShader(loadShader("$path.$gShader"), GL_GEOMETRY_SHADER)

        createShader(loadShader("$path.$vShader"), GL_VERTEX_SHADER)
        createShader(loadShader("$path.$fShader"), GL_FRAGMENT_SHADER)

        glLinkProgram(program)
        glValidateProgram(program)
    }

    private fun createShader(source: String, type: Int): Int {
        val shader = glCreateShader(type)
        if (shader == GL_FALSE)
            logger().throwException("(ID $shader) Error while creating shader.", RuntimeException(), classSource = "XHR_GLShader", statusCode = XH_STATUS_OPENGL_ERROR)

        glShaderSource(shader, source)
        glCompileShader(shader)

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE)
            logger().throwException("(ID $shader) Error while computing shader.", RuntimeException(), classSource = "XHR_GLShader", statusCode = XH_STATUS_OPENGL_ERROR)

        glAttachShader(program, shader)

        return shader
    }

    private fun loadShader(path: String): String {
        val INCLUDE_FUNC = "#include"
        var r = ""

        try {
            BufferedReader(InputStreamReader(FileInputStream(SHADER_PATH + path))).apply {
                for (line in this.lines()) {
                    if (line.startsWith(INCLUDE_FUNC)) {
                        val fileDir = path.split("/")
                        val dir = path.substring(0, path.length - fileDir[fileDir.size - 1].length)
                        r += loadShader(dir + line.substring(INCLUDE_FUNC.length + 2, line.length - 1))
                    } else
                        r += line + "\n"
                }
            }
        } catch(e: Exception) {
            logger().throwException("(Path: $path) Error while parsing source code: ${e.localizedMessage}", RuntimeException(), classSource = "XHR_GLShader", statusCode = XH_STATUS_OPENGL_ERROR)
        }

        return r
    }

    fun bind() = glUseProgram(program)

    fun getUniformLocation(name: String) = glGetUniformLocation(program, name)
    fun getAttribLocation(name: String) = glGetUniformLocation(program, name)

    fun loadInt(location: Int, v: Int) = glUniform1i(location, v)
    fun loadFloat(location: Int, v: Float) = glUniform1f(location, v)
    fun loadVec2f(location: Int, v: Vector2f) = glUniform2f(location, v.x, v.y)
    fun load2f(location: Int, x: Float, y: Float) = glUniform2f(location, x, y)
    fun loadVec3f(location: Int, v: Vector3f) = glUniform3f(location, v.x, v.y, v.z)
    fun load3f(location: Int, x: Float, y: Float, z: Float) = glUniform3f(location, x, y, z)
    fun loadVec4f(location: Int, v: Vector4f) = glUniform4f(location, v.x, v.y, v.z, v.w)
    fun load4f(location: Int, x: Float, y: Float, z: Float, w: Float) = glUniform4f(location, x, y, z, w)
    fun loadMat(location: Int, mat: Matrix4f) {
        stackPush().apply {
            val fb = this.mallocFloat(16)
            mat[fb]
            glUniformMatrix4fv(location, false, fb)
        }
    }

    companion object {
        private val gShader = config()[XHR_CONFIG_GL_GEOMETRY_EXT]
        private val vShader = config()[XHR_CONFIG_GL_VERTEX_EXT]
        private val fShader = config()[XHR_CONFIG_GL_FRAGMENT_EXT]

        val SHADER_PATH = if (gl().modernGL) "res/shaders/std/" else "res/shaders/comp"

        fun unbind() = glUseProgram(0)
    }

}