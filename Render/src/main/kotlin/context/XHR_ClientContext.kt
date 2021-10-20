package context

import XHR_ENGINE
import XHR_OPENGL
import XH_App
import XH_Context
import config
import graphics.XHR_Window
import graphics.gl
import templates.XHR_IRenderLogic

class XHR_ClientContext(app: XH_App) : XH_Context(app), XHR_IRenderLogic {

    lateinit var window: XHR_Window
        private set

    lateinit var projection: String
        private set

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