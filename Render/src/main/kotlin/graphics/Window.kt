package graphics

import XHR_OPENGL
import XHR_VULKAN
import app
import config
import ClientContext
import input.input
import org.joml.Vector2i
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw.GLFWWindowSizeCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.glClearColor
import org.lwjgl.opengl.GL11.glViewport
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL
import templates.IEngine
import utils.LogLevel
import utils.XH_STATUS_GLFW_ERROR
import utils.logger
import java.lang.IllegalStateException
import java.lang.RuntimeException
import kotlin.math.pow

data class GLFWConfiguration(val resizable: Int, val fullscreen: Int, val width: Int, val height: Int,
                             val title: String, val colorBufferBits: Int, val floating: Int, val decoration: Int, val msaa: Int,
                             val centerCursor: Int, val vSync: Int) {
    fun getSize() = Vector2i(width, height)
}

class XHR_Window(val context: ClientContext) : IEngine {
    var window: Long = 0L
        private set

    val dimension = Vector2i(config("window.screenWidth") as Int, config("window.screenHeight") as Int)

    val config: GLFWConfiguration = GLFWConfiguration(
        if (config("window.resizable") as Boolean) GLFW_TRUE else GLFW_FALSE,
        if (config("window.fullscreen") as Boolean) GLFW_TRUE else GLFW_FALSE,
        dimension.x, dimension.y,
        config("window.title") as String,
        config("gl.colorBufferBits") as Int,
        if (config("window.floating") as Boolean) GLFW_TRUE else GLFW_FALSE,
        if (config("window.decorated") as Boolean) GLFW_TRUE else GLFW_FALSE,
        config("gl.multisampling") as Int,
        if (config("window.centerCursor") as Boolean) GLFW_TRUE else GLFW_FALSE,
        if (config("gl.vsync") as Boolean) GLFW_TRUE else GLFW_FALSE
    )

    override fun onInit() {
        GLFWErrorCallback.createPrint(logger().printer).set()

        if (!glfwInit())
            logger().throwException("Unable to initialize GLFW", IllegalStateException(),
                classSource = "XHR_Window", statusCode = XH_STATUS_GLFW_ERROR)

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, config.resizable)
        glfwWindowHint(GLFW_DECORATED, config.decoration)
        glfwWindowHint(GLFW_CENTER_CURSOR, config.centerCursor)
        glfwWindowHint(GLFW_FLOATING, config.floating)

        glfwWindowHint(GLFW_RED_BITS, config.colorBufferBits / 4)
        glfwWindowHint(GLFW_GREEN_BITS, config.colorBufferBits / 4)
        glfwWindowHint(GLFW_BLUE_BITS, config.colorBufferBits / 4)
        glfwWindowHint(GLFW_ALPHA_BITS, config.colorBufferBits / 4)

        if (config.msaa > 0)
            glfwWindowHint(GLFW_SAMPLES, 2.0.pow(config.msaa).toInt())

        window = glfwCreateWindow(dimension.x, dimension.y, config.title, NULL, NULL)

        if (window == NULL)
            logger().throwException("Failed to create the GLFW window", RuntimeException(),
                classSource = "XHR_Window", statusCode = XH_STATUS_GLFW_ERROR)

        stackPush().use {
            val pWidth = it.mallocInt(1)
            val pHeight = it.mallocInt(1)

            glfwGetWindowSize(window, pWidth, pHeight)
            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())
                ?: logger().throwException("Failed to retrieve a screen monitor", RuntimeException(),
                    classSource = "XHR_Window", statusCode = XH_STATUS_GLFW_ERROR)

            glfwSetWindowPos(
                window,
                (vidmode.width() - pWidth[0]) / 2,
                (vidmode.height() - pHeight[0]) / 2
            )
        }

        glfwMakeContextCurrent(window)
        glfwShowWindow(window)

        input().init(window)

        glfwSetKeyCallback(window, input().keyboard)
        glfwSetMouseButtonCallback(window, input().mouse)
        glfwSetWindowSizeCallback(window, object: GLFWWindowSizeCallback() {
            override fun invoke(win: Long, width: Int, height: Int) {
                if (win != window) return

                onResize()
            }
        })

        when (config("window.engine")) {
            XHR_OPENGL -> {
                GL.createCapabilities()
                glClearColor(.0f, .0f, .0f, .0f)
                glViewport(0, 0, dimension.x, dimension.y)
            }
            XHR_VULKAN -> {

            }
        }

        setVSync(config.vSync)

        logger().internal_log("Window created successfully!", LogLevel.FINE, "Window")
    }

    override fun onUpdate() {
        if (glfwWindowShouldClose(window))
            app().stop()

        input().onUpdate()
    }

    override fun onPostRender() {
        glfwSwapBuffers(window)
    }

    override fun onResize() {
        stackPush().use {
            val w = it.mallocInt(1)
            val h = it.mallocInt(1)
            glfwGetWindowSize(window, w, h)

            dimension.set(w[0], h[0])
        }

        glViewport(0, 0, dimension.x, dimension.y)
        context.onResize()
    }

    override fun onDispose() {
        glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }

    fun setCursorMode(mode: Int) = glfwSetInputMode(window, GLFW_CURSOR, mode)
    fun setVSync(interval: Int) = glfwSwapInterval(interval)
    fun setWindowTitle(title: String) = glfwSetWindowTitle(window, title)

}