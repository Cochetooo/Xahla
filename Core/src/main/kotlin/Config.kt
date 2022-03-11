import templates.ICoreEngine
import utils.*
import java.io.File

/** Configuration Handling
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
object Config : ICoreEngine {

    private val properties: MutableMap<String, Any> = mutableMapOf()

    private const val configPath = "configs"

    operator fun get(key: String): Any? = properties[key]
    operator fun set(key: String, value: Any) {
        properties[key] = value
    }

    override fun onAwake() {
        File(configPath).walk().forEach { file ->
            if (file.path == configPath) return@forEach

            logger().internal_log("File: " + file.path, XH_LogLevel.CONFIG, "Config")

            properties.putAll(compileFiles(file.nameWithoutExtension, file.readLines()))
        }
    }

    override fun onExit() {

    }

    private fun compileFiles(category: String, content: List<String>): MutableMap<String, Any> {
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

            if (varValue.contains("env(")) {
                val params = varValue.substring(4, varValue.lastIndexOf(")"))
            }

            when (varType) {
                "String" -> list["$category.$varName"] = varValue
                "Int" -> list["$category.$varName"] = varValue.toInt()
                "Float" -> list["$category.$varName"] = varValue.toFloat()
                "Boolean" -> list["$category.$varName"] = varValue.toBoolean()
            }
        }

        logger().internal_log(list.toString(), XH_LogLevel.CONFIG, "Config")

        return list
    }
}

fun config(name: String): Any? = Config[name]