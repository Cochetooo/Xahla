import components.XH_CTransform
import templates.XH_ICoreLogic

fun main() {
    Main()
}

class Main : XH_ICoreLogic {

    init {
        app().build(XH_Context::class.java, this)
        app().start()

        // Prohibited to do something here
    }

    override fun onInit() {
        context().add(MyFirstObject("Obj 1"))
    }

    override fun onSecond() {

    }

    override fun onExit() {
        println("Exit")
    }

}

class MyFirstObject(name: String = "MyFirstObject") : XH_Object(name) {
    val transform = XH_CTransform(this, name="Transform 1")

    override fun onInit() {
        add(transform)
    }

    override fun onSecond() {
        transform.translate(0.5f, .0f, .0f)
        println(transform.position)

        if (transform.position.x > 3)
            app().stop()
    }
}