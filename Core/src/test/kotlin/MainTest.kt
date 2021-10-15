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
        context().add(MyFirstObject("Obj 2"))
    }

    override fun onSecond() {
        for (obj in context().getObjectsByClass(MyFirstObject::class.java))
            println(obj)

        app().stop()
    }

    override fun onExit() {
        println("Goodbye")
    }

}

class MyFirstObject(name: String = "MyFirstObject") : XH_Object(name) {}