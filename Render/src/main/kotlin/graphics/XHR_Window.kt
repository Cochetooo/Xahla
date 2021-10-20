package graphics

import XHR_ENGINE
import XHR_OPENGL
import XHR_VULKAN
import config
import context.XHR_ClientContext
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
import templates.XHR_IRenderLogic
import utils.XH_Logger
import utils.logger
import java.lang.IllegalStateException
import java.lang.Math.pow
import java.lang.RuntimeException
import kotlin.math.pow

class XHR_Window(val context: XHR_ClientContext) : XHR_IRenderLogic {
    var window: Long = 0L
        private set

    val windowSize = Vector2i(config()["rendering.initial_width"] as Int,
        config()["rendering.initial_height"] as Int)

    val config: XHR_GLFWConfiguration = XHR_GLFWConfiguration(
        if (config()["rendering.resizable"] as Boolean) GLFW_TRUE else GLFW_FALSE,
        if (config()["rendering.fullscreen"] as Boolean) GLFW_TRUE else GLFW_FALSE,
        windowSize.x, windowSize.y,
        config()["rendering.window_title"] as String,
        config()["rendering.color_buffer_bits"] as Int,
        if (config()["rendering.floating"] as Boolean) GLFW_TRUE else GLFW_FALSE,
        if (config()["rendering.decoration"] as Boolean) GLFW_TRUE else GLFW_FALSE,
        config()["rendering.msaa"] as Int,
        if (config()["rendering.center_cursor"] as Boolean) GLFW_TRUE else GLFW_FALSE,
        if (config()["rendering.vsync"] as Boolean) GLFW_TRUE else GLFW_FALSE
    )

    override fun onInit() {
        GLFWErrorCallback.createPrint(logger().printer).set()

        if (!glfwInit())
            logger().throwException("Unable to initialize GLFW", IllegalStateException())

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

        window = glfwCreateWindow(windowSize.x, windowSize.y, config.title, NULL, NULL)

        if (window == NULL)
            logger().throwException("Failed to create the GLFW window", RuntimeException())

        stackPush().apply {
            val pWidth = this.mallocInt(1)
            val pHeight = this.mallocInt(1)

            glfwGetWindowSize(window, pWidth, pHeight)
            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())
                ?: logger().throwException("Failed to retrieve a screen monitor", RuntimeException())

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

        when (XHR_ENGINE) {
            XHR_OPENGL -> {
                GL.createCapabilities()
                glClearColor(.0f, .0f, .0f, .0f)
                glViewport(0, 0, windowSize.x, windowSize.y)
            }
            XHR_VULKAN -> {

            }
        }

        setVSync(config.vSync)
    }

    override fun onUpdate() {
        if (glfwWindowShouldClose(window))
            context.onDispose()

        input().onUpdate()
    }

    override fun onPostRender() {
        glfwSwapBuffers(window)
    }

    override fun onResize() {
        stackPush().apply {
            val w = this.mallocInt(1)
            val h = this.mallocInt(1)
            glfwGetWindowSize(window, w, h)

            windowSize.set(w[0], h[0])
        }

        glViewport(0, 0, windowSize.x, windowSize.y)
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