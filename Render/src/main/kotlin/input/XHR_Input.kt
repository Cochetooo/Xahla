package input

import org.joml.Vector2d
import org.joml.Vector2f
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWKeyCallback
import org.lwjgl.glfw.GLFWMouseButtonCallback
import org.lwjgl.glfw.GLFWScrollCallback
import org.lwjgl.system.MemoryStack.stackPush

/** User Input handling
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
object XHR_Input {
    private var window = 0L
    private val KEYBOARD_SIZE = 512
    private val MOUSE_SIZE = 16

    private val keyStates = IntArray(KEYBOARD_SIZE)
    private val activeKeys = BooleanArray(KEYBOARD_SIZE)

    private val mouseButtonStates = IntArray(MOUSE_SIZE)
    private val activeMouseButtons = BooleanArray(MOUSE_SIZE)

    private var lastMouseNS = 0L
    private var mouseDoubleClickPeriodNS = 1_000_000_000L / 5

    private val NO_STATE = -1

    private val scrollOffsets = Vector2d(0.0, 0.0)

    fun init(pWindow: Long) {
        window = pWindow

        resetKeyboard()
        resetMouse()
    }

    val keyboard = object: GLFWKeyCallback() {
        override fun invoke(win: Long, key: Int, scancode: Int, action: Int, mods: Int) {
            if (win != window) return

            activeKeys[key] = action != GLFW_RELEASE
            keyStates[key] = action
        }
    }

    val mouse = object: GLFWMouseButtonCallback() {
        override fun invoke(win: Long, button: Int, action: Int, mods: Int) {
            if (win != window) return

            activeMouseButtons[button] = action != GLFW_RELEASE
            mouseButtonStates[button] = action
        }
    }

    val scroll = object: GLFWScrollCallback() {
        override fun invoke(win: Long, xoffset: Double, yoffset: Double) {
            if (win != window) return

            scrollOffsets.x = xoffset
            scrollOffsets.y = yoffset
        }
    }

    fun onUpdate() {
        resetKeyboard()
        resetMouse()

        glfwPollEvents()
    }

    private fun resetKeyboard() {
        for (i in keyStates.indices)
            keyStates[i] = NO_STATE
    }

    private fun resetMouse() {
        for (i in mouseButtonStates.indices)
            mouseButtonStates[i] = NO_STATE

        val now = System.nanoTime()

        if (now - lastMouseNS > mouseDoubleClickPeriodNS)
            lastMouseNS = 0
    }

    fun keyDown(key: Int): Boolean = activeKeys[key]

    fun keyPressed(key: Int): Boolean = keyStates[key] == GLFW_PRESS

    fun keyReleased(key: Int): Boolean = keyStates[key] == GLFW_RELEASE

    fun mouseButtonDown(button: Int): Boolean = activeMouseButtons[button]

    fun mouseButtonPressed(button: Int): Boolean = mouseButtonStates[button] == GLFW_PRESS

    fun mouseButtonReleased(button: Int): Boolean {
        val flag = mouseButtonStates[button] == GLFW_RELEASE

        if (flag)
            lastMouseNS = System.nanoTime()

        return flag
    }

    fun mouseButtonDoubleClicked(button: Int): Boolean {
        val last = lastMouseNS
        val flag = mouseButtonReleased(button)

        val now = System.nanoTime()

        if (flag && now - last < mouseDoubleClickPeriodNS) {
            lastMouseNS = 0
            return true
        }

        return false
    }

    fun getMousePosition(): Vector2f {
        stackPush().apply {
            val xBuffer = BufferUtils.createDoubleBuffer(1)
            val yBuffer = BufferUtils.createDoubleBuffer(1)
            glfwGetCursorPos(window, xBuffer, yBuffer)

            return Vector2f(xBuffer[0].toFloat(), yBuffer[0].toFloat())
        }
    }

    fun getScrollWheel() = scrollOffsets.y.toFloat()

    fun setRawMouseMotion(option: Boolean) {
        if (glfwGetInputMode(window, GLFW_CURSOR) == GLFW_CURSOR_NORMAL)
            return

        if (!glfwRawMouseMotionSupported())
            return

        glfwSetInputMode(window, GLFW_RAW_MOUSE_MOTION, if (option) GLFW_TRUE else GLFW_FALSE)
    }
}

fun input() = XHR_Input