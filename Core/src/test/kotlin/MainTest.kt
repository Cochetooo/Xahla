import utils.ucfirst
import java.io.File
import java.net.URL
import java.net.URLClassLoader

fun main() {
    loadClasses("configs")
}

fun loadClasses(path: String): MutableMap<String, Any> {
    val list: MutableMap<String, Any> = mutableMapOf()

    File(path).walk().forEach { file ->
        if (file.path == path) return@forEach

        println(file.path + " | Directory: ${file.isDirectory}")

       list.putAll(compileFiles(file.readLines()))
    }

    return list
}

fun compileFiles(content: List<String>): MutableMap<String, Any> {
    val list: MutableMap<String, Any> = mutableMapOf()

    for (line in content) {
        if (line.startsWith("//") || line.isBlank()) continue

        var text = ""
        var inText = false
        for (c in line.toCharArray()) {
            if (c == '"')
                inText = !inText

            if (!inText) {
                if (c == ' ' || c == '\t')
                    continue
            }

            text += c
        }

        val varName = text.substring(3, text.indexOf(":"))
        val varType = text.substring(text.indexOf(":") + 1, text.indexOf("="))
        val varValue = text.substring(text.indexOf("=") + 1)

        println("$varName : $varType = $varValue")
    }

    return list
}