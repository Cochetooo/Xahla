import langs.en.helpers
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

val currentFolder = System.getProperty("user.dir")

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("List of available command:\n")

        for (i in helpers)
            println("--- ${i.key} -> ${i.value}")

        return
    }

    when (args[0]) {
        "create:project" -> {
            if (args.size == 1)
                throw IllegalArgumentException("create:project -> Provide a project name")

            val name = args[1].replace("[^A-Za-z0-9]", "")

            createProject(name)
        }

        "log:clear" -> {
            logClear()
        }

        "show:path" -> {
            showPath()
        }
    }
}

fun createProject(name: String) {
    println("Creating project $name ...")

    val root = File("$currentFolder/$name")

    if (!root.exists())
        root.mkdir()

    val sourceDirectoryLocation = "vendor/templates/sample_project"

    Files.walk(Paths.get(sourceDirectoryLocation)).forEach { source ->
        val destination = Paths.get(root.path, source.toString().substring(sourceDirectoryLocation.length))

        try {
            Files.copy(source, destination)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    println("Project created successfully!")
}

fun logClear() {
    File("logs/").deleteRecursively()
    println("Logs deleted successfully!")
}

fun showPath() {
    println("Current Working Directory: ${File(".").absolutePath}")
}