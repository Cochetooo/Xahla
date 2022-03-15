package graphics.opengl

import config
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW.glfwGetCurrentContext
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE
import org.lwjgl.opengl.GL14.GL_TEXTURE_LOD_BIAS
import org.lwjgl.opengl.GL30.glGenerateMipmap
import utils.LogLevel
import utils.XH_STATUS_OPENGL_ERROR
import utils.logger
import java.io.File
import java.io.IOException
import java.nio.IntBuffer
import javax.imageio.ImageIO

internal data class GLTextureData(val id: Int, val width: Int, val height: Int, val buffer: IntBuffer)

class GLTexture
    internal constructor(private val id: Int, val width: Int, val height: Int, val mipmap: Boolean) {

    fun bind() = glBindTexture(GL_TEXTURE_2D, id)

    companion object {
        private val cache: MutableMap<String, GLTexture> = mutableMapOf()
        private val textures: MutableList<Int> = mutableListOf()

        private fun createTexture(): Int {
            val texture = glGenTextures()
            textures += texture

            return texture
        }

        internal fun clearCache() {
            textures.forEach { glDeleteTextures(it) }
            textures.clear()
        }

        @JvmOverloads internal fun loadTexture(path: String, format: Int = GL_RGBA, filter: Int = GL_NEAREST, textureWrap: Int = GL_CLAMP_TO_EDGE, mipmap: Boolean = false): GLTexture {
            if (cache.containsKey(path))
                return cache[path]
                    ?: logger().throwException("The texture cache returned a null texture at: $path", NullPointerException(), "GLTexture", XH_STATUS_OPENGL_ERROR)

            val texture = decode(path)

            val id = texture.id
            val width = texture.width
            val height = texture.height

            glBindTexture(GL_TEXTURE_2D, id)

            if ((mipmap && !gl().modernGL))
                logger().internal_log("Disabling mipmap on texture $path : Version of OpenGL is too old!", LogLevel.WARNING, "GLTexture")

            if (mipmap && gl().modernGL) {
                if (filter == GL_LINEAR) {
                    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR.toFloat())
                    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR.toFloat())
                } else if (filter == GL_NEAREST) {
                    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST.toFloat())
                    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST_MIPMAP_NEAREST.toFloat())
                }

                glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_LOD_BIAS, -1.0f)
            } else {
                glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter.toFloat())
                glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter.toFloat())
            }

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, textureWrap)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, textureWrap)

            glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, texture.buffer)

            if (mipmap && gl().modernGL)
                glGenerateMipmap(GL_TEXTURE_2D)

            val finalTexture = GLTexture(id, width, height, mipmap)
            cache[path] = finalTexture

            return finalTexture
        }

        private fun decode(path: String): GLTextureData {
            var pixels: IntArray? = null
            var width = 0; var height = 0

            try {
                val image = ImageIO.read(File(path))
                width = image.width
                height = image.height
                pixels = IntArray(width * height)
                image.getRGB(0, 0, width, height, pixels, 0, width)
            } catch (e: IOException) {
                logger().throwException("Impossible to decode texture data: $path", e, "GLTexture", XH_STATUS_OPENGL_ERROR)
            }

            val data = IntArray(pixels.size)
            for (i in pixels.indices) {
                val a = (pixels[i] and 0xff000000.toInt()) shr 24
                val r = (pixels[i] and 0xff0000) shr 16
                val g = (pixels[i] and 0xff00) shr 8
                val b = (pixels[i] and 0xff)

                data[i] = (a shl 24) or (b shl 16) or (g shl 8) or r
            }

            val buffer = BufferUtils.createIntBuffer(data.size)
            buffer.put(data)
            buffer.flip()

            return GLTextureData(createTexture(), width, height, buffer)
        }
    }
}

fun loadTexture(path: String, format: Int = GL_RGBA, filter: Int = GL_NEAREST, textureWrap: Int = GL_CLAMP_TO_EDGE, mipmap: Boolean = false): GLTexture
    = GLTexture.loadTexture(path, format, filter, textureWrap, mipmap)

fun unbindTextures() = glBindTexture(GL_TEXTURE_2D, 0)