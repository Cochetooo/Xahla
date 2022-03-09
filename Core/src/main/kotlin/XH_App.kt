import templates.XH_IApp
import templates.XH_ICoreLogic
import templates.XH_ILogic
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
object XH_App : XH_IApp, XH_ICoreLogic {
    private lateinit var app: XH_ICoreLogic
    var paused = false
        set(value) {
            if (!value)
                onResume()

            field = value
        }

    var ups = 0
        private set
    private var tick = 0;

    private var tickTime = 0.0;
    private var running: Boolean = false
    override lateinit var context: XH_Context

    /**
     * Instantiate the program app.
     */
    @JvmOverloads
    override fun build(pContext: Class<out XH_Context>, pApp: XH_ICoreLogic) {
        this.app = pApp

        onAwake()

        xh_tryCatch {
            context = pContext.getConstructor(XH_App::class.java).newInstance(this)
            context.onAwake()

            this.tick = config()[XH_CONFIG_UPS] as Int

            onInit()
        }
    }

    override fun start() {
        if (running)
            logger().throwException("The program has already started.", IllegalStateException(),
                classSource = "XH_App", statusCode = XH_STATUS_GENERAL_ERROR)

        onPostInit()
        running = true

        var ticks = 0
        tickTime = 1_000_000_000.0 / tick

        var updatedTick = 0.0
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

private val app: XH_IApp
    get() {
        XH_Config.onAwake()

        var clazz: KClass<out XH_IApp>? = null

        if (config()[XH_CONFIG_CLASS] == null) {
            logger().wLog("App class not defined! Choosing XH_App by default.")
            clazz = XH_App.javaClass.kotlin
        } else {
            xh_tryCatch ({
                clazz = Class.forName(config()[XH_CONFIG_CLASS] as String).kotlin as KClass<out XH_IApp>

            }, "App Class name is not found: ${config()[XH_CONFIG_CLASS]}", ClassNotFoundException::class.java)
        }

        return clazz!!.objectInstance ?: logger().throwException("App is not a valid object class", IllegalArgumentException(),
            classSource = "XH_App", statusCode = XH_STATUS_GENERAL_ERROR)
    }

/**
 * app points to the program whatever if it is a custom or default app.
 */
fun app(): XH_IApp = app
fun context(): XH_Context = app().context