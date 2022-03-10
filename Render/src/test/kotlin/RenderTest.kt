import context.XHR_ClientContext
import templates.XHR_ICoreRenderLogic
import utils.XH_LogLevel
import utils.logger

fun main() {
    Main()
}

class Main : XHR_ICoreRenderLogic {

    init {
        logger().logLevel = XH_LogLevel.ALL
        app().build(XHR_ClientContext::class.java, this)
        app().start()

        // Prohibited to do something here
    }

    override fun onSecond() {
        clientContext().window.setWindowTitle("Simple App | UPS: ${(app() as XHR_RenderApp).ups} FPS: ${(app() as XHR_RenderApp).fps}")
    }

    override fun onExit() {
        println("Exit")
    }

}