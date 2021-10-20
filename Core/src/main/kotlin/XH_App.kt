import templates.XH_ICoreLogic
import templates.XH_ILogic
import utils.XH_Logger
import utils.XH_Timer
import utils.xh_tryCatch
import kotlin.system.exitProcess

/** App instance
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
object XH_App : XH_ICoreLogic {
    private lateinit var app: XH_ICoreLogic
    private var running = false;
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

    internal lateinit var context: XH_Context

    /**
     * Instantiate the program app.
     */
    @JvmStatic
    fun build(pContext: Class<out XH_Context>, pApp: XH_ICoreLogic, ups: Int = 50) {
        this.app = pApp
        this.tick = ups

        onAwake()

        xh_tryCatch {
            context = pContext.getConstructor(XH_App::class.java).newInstance(this)
            onInit()
        }
    }

    @JvmStatic
    fun start() {
        if (running)
            XH_Logger.throwException("The program has already started.")

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

    @JvmStatic
    fun stop() {
        onDispose()
    }

    override fun onAwake() {
        app.onAwake()
        XH_Config.onAwake()
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

fun app(): XH_App = XH_App
fun context(): XH_Context = app().context