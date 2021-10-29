package context

import XHR_CONFIG_DEFAULT_FPS
import XHR_CONFIG_FPS
import XHR_ENGINE
import XHR_OPENGL
import XH_App
import XH_Context
import config
import graphics.XHR_Window
import graphics.gl
import templates.XHR_ICoreRenderLogic
import templates.XHR_IRenderLogic
import templates.XH_IApp
import utils.XH_CONFIG_DEFAULT_UPS
import utils.XH_STATUS_GENERAL_ERROR
import utils.logger
import java.lang.IllegalStateException

class XHR_ClientContext(app: XH_IApp) : XH_Context(app), XHR_IRenderLogic {

    lateinit var window: XHR_Window
        private set

    lateinit var projection: String
        private set

    override fun onAwake() {
        super.onAwake()

        /*
        "projection": "2d",
  "engine": "opengl",
  "standard_opengl_version": 4.0,
  "initial_width": 1280,
  "initial_height": 720,
  "resizable": true,
  "fullscreen": false,
  "window_title": "Simple Demo",
  "color_buffer_bits": 32,
  "floating": true,
  "decoration": true,
  "msaa": 0,
  "center_cursor": true,
  "vsync": false

         */

        if (config()[XHR_CONFIG_FPS] == null)
            config()[XHR_CONFIG_FPS] = XHR_CONFIG_DEFAULT_FPS
        else {
            if (config()[XHR_CONFIG_FPS] !is Int)
                logger().throwException("configs/app.json : $XHR_CONFIG_FPS must be an integer.", IllegalStateException(),
                    classSource = "XHR_ClientContext", statusCode = XH_STATUS_GENERAL_ERROR
                )
        }

        if (config()[XHR_CONFIG_FPS] == null)
            config()[XHR_CONFIG_FPS] = XHR_CONFIG_DEFAULT_FPS
        else {
            if (config()[XHR_CONFIG_FPS] !is Int)
                logger().throwException("configs/app.json : $XHR_CONFIG_FPS must be an integer.", IllegalStateException(),
                    classSource = "XHR_ClientContext", statusCode = XH_STATUS_GENERAL_ERROR
                )
        }
    }

    override fun onInit() {
        super<XH_Context>.onInit()

        projection = config()["rendering.projection"] as String

        window = XHR_Window(this)
        window.onInit()

        when (XHR_ENGINE) {
            XHR_OPENGL -> gl().init(this)
        }
    }

    override fun onUpdate() {
        super<XH_Context>.onUpdate()
        window.onUpdate()
    }

    override fun onPostUpdate() {
        super<XH_Context>.onPostUpdate()
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
        super<XH_Context>.onDispose()
    }

}