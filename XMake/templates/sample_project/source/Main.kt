import interfaces.ICoreEngine

fun main() {
    Main()
}

class Main : ICoreEngine {

    init {
        println("Hello world!")

        app().build(this)
        app().start()

        // Not recommended to write code here.
    }

}