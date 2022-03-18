import graphics.XHR_Window
import graphics.opengl.GLShader
import graphics.opengl.gl
import graphics.opengl.unbindShaders
import objects.Entity
import objects.OrthographicCamera
import objects.PerspectiveCamera
import templates.IEngine
import utils.XH_STATUS_GENERAL_ERROR
import utils.logger
import kotlin.IllegalStateException

open class ClientContext(app: App) : Context(app) {

    lateinit var window: XHR_Window
        private set

    lateinit var projection: String
        private set

    var worldShader: GLShader? = null
        private set

    override fun onAwake() {
        validateConfigs(mapOf(
            "window.projection" to PropertyType.STRING,
            "window.engine" to PropertyType.STRING,
            "window.decorated" to PropertyType.BOOLEAN,
            "window.fullscreen" to PropertyType.BOOLEAN,
            "window.resizable" to PropertyType.BOOLEAN,
            "window.title" to PropertyType.STRING,
            "window.visible" to PropertyType.BOOLEAN,
            "window.centerCursor" to PropertyType.BOOLEAN,
            "window.floating" to PropertyType.BOOLEAN,
            "window.inputFocus" to PropertyType.BOOLEAN,
            "window.maximize" to PropertyType.BOOLEAN,
            "window.screenWidth" to PropertyType.INT,
            "window.screenHeight" to PropertyType.INT,

            "gl.modernGLVersion" to PropertyType.STRING,
            "gl.vsync" to PropertyType.BOOLEAN,
            "gl.attribLocationPosition" to PropertyType.INT,
            "gl.attribLocationColor" to PropertyType.INT,
            "gl.attribLocationNormal" to PropertyType.INT,
            "gl.attribLocationTexCoord" to PropertyType.INT,
            "gl.colorBufferBits" to PropertyType.INT,
            "gl.multisampling" to PropertyType.INT
        ))

        super.onAwake()
    }

    override fun onInit() {
        window = XHR_Window(this)
        window.onInit()

        projection = config("window.projection") as String
        if (projection == "3d")
            this.add(PerspectiveCamera())
        else if (projection == "2d")
            this.add(OrthographicCamera("MainCamera"))

        when (config("window.engine")) {
            XHR_OPENGL -> gl().init(this)
        }

        super.onInit()

        this.worldShader = GLShader(this, "world", false)
    }

    override fun onUpdate() {
        super.onUpdate()
        window.onUpdate()
    }

    override fun onPostUpdate() {
        super.onPostUpdate()
        window.onPostUpdate()
    }

    override fun onRender() {
        // Render detached objects from worldShader
        for (obj in objects) {
            if (obj is Entity) {
                if (obj.detached)
                    obj.onRender()
            }
        }

        /* worldShader!!.bind()
        worldShader!!.loadMat(worldShader!!.getUniformLocation("projectionMatrix"), (this["MainCamera"] as OrthographicCamera).projection.projection)

        for (obj in objects) {
            if (obj is Entity) {
                if (!obj.detached)
                    obj.onRender()
            }
        }

        unbindShaders() */
    }

    override fun onPostRender() {
        window.onPostRender()
        super.onPostRender()
    }

    override fun onDispose() {
        window.onDispose()
        super.onDispose()
    }

}

fun clientContext(): ClientContext = app().context as ClientContext