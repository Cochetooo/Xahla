import templates.ICoreEngine
import utils.LogLevel
import utils.logger

fun main() {
    Main()
}

class Main : ICoreEngine {

    init {
        logger().excepts = arrayOf()
        app().build(ClientContext::class.java, this)
        app().start()

        // Prohibited to do something here
    }

    override fun onInit() {
        logger().log(logger().logLevel)
    }

    override fun onSecond() {
        clientContext().window.setWindowTitle("Simple App | UPS: ${app().ups} FPS: ${app().fps}")
    }

    override fun onExit() {
        println("Exit")
    }

}