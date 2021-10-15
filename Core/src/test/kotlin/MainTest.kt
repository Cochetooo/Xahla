import templates.XH_ICoreLogic

fun main() {
    Main()
}

class Main : XH_ICoreLogic {

    constructor() {
        app().build(XH_Context::class.java, this)
        app().start()

        // Prohibited to do something here
    }

    override fun onInit() {
        context().add(MyFirstObject())
    }

    override fun onExit() {
        println("Goodbye")
    }

}

class MyFirstObject : XH_Object("MyFirstObject") {
    override fun onSecond() {
        super.onSecond()
        println("Hello!")
        app().stop()
    }
}