import context.XHR_ClientContext
import templates.*
import utils.*
import java.lang.IllegalStateException
import java.lang.reflect.InvocationTargetException

import kotlin.reflect.KClass
import kotlin.system.exitProcess

/** App instance
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
object XHR_RenderApp : XH_IApp, XHR_ICoreRenderLogic {
    private lateinit var app: XHR_ICoreRenderLogic
    var paused = false
        set(value) {
            if (!value)
                onResume()

            field = value
        }

    var ups = 0
        private set
    var fps = 0
        private set
    private var tick = 0; private var render = 0

    private var tickTime = 0.0; private var renderTime = 0.0
    private var running: Boolean = false
    override lateinit var context: XH_Context

    /**
     * Instantiate the program app.
     */
    @JvmOverloads
    override fun build(pContext: Class<out XH_Context>, pApp: XH_ICoreLogic) {
        if (pApp !is XHR_ICoreRenderLogic)
            logger().throwException("App must inherits from render logic. Found: ${pApp.javaClass.simpleName}", IllegalArgumentException(),
                classSource="XHR_RenderApp", statusCode=XH_STATUS_GENERAL_ERROR)

        this.app = pApp

        onAwake()

        xh_tryCatch {
            context = pContext.getConstructor(XH_IApp::class.java).newInstance(this)
            context.onAwake()

            this.tick = config()[XH_CONFIG_UPS] as Int
            this.render = config()[XHR_CONFIG_FPS] as Int

            if (this.render > 1_000_000_000 || this.render < 0)
                this.render = 1_000_000_000

            onInit()
        }
    }

    override fun start() {
        if (running)
            logger().throwException("The program has already started.", IllegalStateException(),
                classSource = "XHR_RenderApp", statusCode = XH_STATUS_GENERAL_ERROR)

        onPostInit()
        running = true

        var ticks = 0
        tickTime = 1_000_000_000.0 / tick

        var frames = 0
        renderTime = 1_000_000_000.0 / render

        var updatedTick = 0.0; var renderedTick = 0.0
        var secondTime = 0

        val timer = XH_Timer()

        while (running) {
            if (paused) {
                onPause()
                return
            }

            if (timer.elapsed - updatedTick >= tickTime) {
                onUpdate()
                onPostUpdate()

                ticks++
                secondTime++

                if (secondTime % tick == 0) {
                    secondTime = 0
                    ups = ticks
                    fps = frames

                    onSecond()

                    ticks = 0
                    frames = 0
                }

                updatedTick += tickTime
            } else if (timer.elapsed - renderedTick >= renderTime) {
                onRender()
                onPostRender()

                frames++
                renderedTick += renderTime
            } else {
                xh_tryCatch {
                    Thread.sleep(1)
                }
            }
        }

        onExit()
    }

    override fun stop() {
        onDispose()
    }

    override fun onAwake() {
        app.onAwake()
    }

    override fun onInit() {
        context.onInit()
        app.onInit()
    }

    override fun onPostInit() {
        context.onPostInit()
        app.onPostInit()
    }

    override fun onUpdate() {
        app.onUpdate()
        context.onUpdate()
    }

    override fun onPostUpdate() {
        app.onPostUpdate()
        context.onPostUpdate()
    }

    override fun onRender() {
        app.onRender()
        clientContext().onRender()
    }

    override fun onPostRender() {
        app.onPostRender()
        clientContext().onPostRender()
    }

    override fun onSecond() {
        app.onSecond()
        context.onSecond()
    }

    override fun onPause() {
        app.onPause()
        context.onPause()
    }

    override fun onResume() {
        app.onResume()
        context.onResume()
    }

    override fun onDispose() {
        app.onDispose()
        context.onDispose()

        running = false
    }

    override fun onExit() {
        app.onExit()
        context.onExit()

        exitProcess(0)
    }

}

fun clientContext(): XHR_ClientContext {
    if (app() is XHR_RenderApp)
        return app().context as XHR_ClientContext
    logger().throwException("Cannot invoke client context in a non-client app.", IllegalStateException(),
        classSource = "XHR_RenderApp", statusCode = XH_STATUS_GENERAL_ERROR)
}