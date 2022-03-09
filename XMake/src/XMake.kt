import java.io.File

val currentFolder = System.getProperty("user.dir")

fun main(args: Array<String>) {
    if (args.size === 0) {
        println("List of available command:\n--- create:project -> Create a new project")
        return
    }

    when (args[0]) {
        "create:project" -> {
            if (args.size == 1)
                throw IllegalArgumentException("create:project -> Provide a project name")

            val name = args[1].replace("[^A-Za-z0-9]", "")

            if (args.size == 3)
                createProject(name, args[2])

            createProject(name)
        }
    }
}

fun createProject(name: String, clazz: String = "XHR_RenderApp") {
    println("Creating project $name ...")

    if (clazz.contains("[^A-Za-z_]"))
        throw IllegalArgumentException("create:project -> Class name not valid")

    val root = File("$currentFolder/$name")
    root.mkdir()

    val configs = File(root.absolutePath + "/configs")
    configs.mkdir()
    if (clazz.contains("Render")) {
        val rendering = File(configs.absolutePath + "/rendering")
        rendering.mkdir()
    }


    val resources = File(root.absolutePath + "/resources")
    resources.mkdir()
    val assets = File(resources.absolutePath + "/assets")
    assets.mkdir()
    val shaders = File(resources.absolutePath + "/shaders")
    shaders.mkdir()

    val source = File(root.absolutePath + "/source")
    source.mkdir()
    val components = File(source.absolutePath + "/components")
    components.mkdir()
    val objects = File(source.absolutePath + "/objects")
    objects.mkdir()

}