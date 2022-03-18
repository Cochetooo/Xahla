import templates.IEngine
import utils.logger

fun main() {
    Main()
}

class Main : IEngine {
    init {
        logger().excepts = arrayOf("Config")
        app().build(ClientContext::class.java, this)
        app().run()

        // Prohibited to do something here
    }

    override fun onSecond() {
        clientContext().window.setWindowTitle("${config("window.title")} | UPS: ${app().ups} FPS: ${app().fps}")
        val x = 5 / 0
    }

}