import templates.ICoreEngine
import utils.*
import java.lang.IllegalStateException
import kotlin.reflect.KClass
import kotlin.system.exitProcess

/** App instance
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
object XH_App : ICoreEngine {
    private lateinit var app: ICoreEngine
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

    private var tick = 0; private var frame = 0
    private var tickTime = 0.0; private var renderTime = 0.0

    private var running: Boolean = false
    lateinit var context: Context

    /**
     * Instantiate the program app.
     */
    @JvmOverloads fun build(pContext: Class<out Context>, pApp: ICoreEngine) {
        this.app = pApp

        onAwake()

        xh_tryCatch {
            context = pContext.getConstructor(XH_App::class.java).newInstance(this)
            context.onAwake()

            this.tick = config("app.updatePerSecond") as Int
            this.frame = config("app.framePerSecond") as Int

            onInit()
        }
    }

    fun start() {
        if (running)
            logger().throwException("The program has already started.", IllegalStateException(),
                classSource = "XH_App", statusCode = XH_STATUS_GENERAL_ERROR)

        onPostInit()
        running = true

        var ticks = 0; var frames = 0
        tickTime = 1_000_000_000.0 / tick
        renderTime = 1_000_000_000.0 / frame

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

                    onSecond()

                    ticks = 0
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

    fun stop() {
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

    override fun onSecond() {
        app.onSecond()
        context.onSecond()
    }

    override fun onRender() {
        context.onRender()
        app.onRender()
    }

    override fun onPostRender() {
        context.onPostRender()
        app.onPostRender()
    }

    override fun onResize() {
        context.onResize()
        app.onResize()
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

/**
 * app points to the program whatever if it is a custom or default app.
 */
fun app(): XH_App = XH_App
fun context(): Context = app().context