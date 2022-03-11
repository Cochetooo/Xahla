import graphics.XHR_Window
import graphics.opengl.gl
import templates.IEngine
import utils.XH_STATUS_GENERAL_ERROR
import utils.logger
import kotlin.IllegalStateException

class ClientContext(app: XH_App) : Context(app), IEngine {

    lateinit var window: XHR_Window
        private set

    lateinit var projection: String
        private set

    override fun onAwake() {
        super.onAwake()

        if (config("app.framePerSecond") == null)
            logger().throwException("configs/app.framePerSecond is not found. Please restore app.kt", IllegalStateException(),
                classSource = "ClientContext", statusCode = XH_STATUS_GENERAL_ERROR)
        else {
            if (config("app.framePerSecond") !is Int)
                logger().throwException("configs/app.framePerSecond must be an integer.", IllegalStateException(),
                    classSource = "ClientContext", statusCode = XH_STATUS_GENERAL_ERROR
                )
        }
    }

    override fun onInit() {
        super<Context>.onInit()

        projection = config("window.projection") as String

        window = XHR_Window(this)
        window.onInit()

        when (XHR_ENGINE) {
            XHR_OPENGL -> gl().init(this)
        }
    }

    override fun onUpdate() {
        super<Context>.onUpdate()
        window.onUpdate()
    }

    override fun onPostUpdate() {
        super<Context>.onPostUpdate()
        window.onPostUpdate()
    }

    override fun onRender() {
        for (obj in objects) {

        }

        var i = 0
        for (obj in objects) {

        }

        for (obj in objects) {

        }
    }

    override fun onPostRender() {
        window.onPostRender()
    }

    override fun onDispose() {
        window.onDispose()
        super<Context>.onDispose()
    }

}

fun clientContext(): ClientContext = app().context as ClientContext