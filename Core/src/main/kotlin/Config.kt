import templates.IEngine
import utils.*
import java.io.File

/** Configuration Handling
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
object Config : IEngine {

    private val properties: MutableMap<String, Any> = mutableMapOf()

    private const val configPath = "configs"

    val validations = mutableMapOf("app.appName" to PropertyType.STRING,
        "app.appVersion" to PropertyType.STRING,
        "app.appAuthor" to PropertyType.STRING,
        "app.appEnvironment" to PropertyType.STRING,
        "app.updatePerSecond" to PropertyType.INT,
        "app.framePerSecond" to PropertyType.INT,
        "debugger.internalLogging" to PropertyType.BOOLEAN,
        "debugger.logExceptionFile" to PropertyType.BOOLEAN,
        "debugger.logLevel" to PropertyType.STRING,
        "debugger.prefix" to PropertyType.BOOLEAN
    )

    operator fun get(key: String): Any? = properties[key]
    operator fun set(key: String, value: Any) {
        properties[key] = value
    }

    override fun onAwake() {
        File(configPath).walk().forEach { file ->
            if (file.path == configPath || file.isDirectory) return@forEach

            logger().internal_log("File: " + file.path, LogLevel.CONFIG, "Config")

            properties.putAll(compileFiles(file.nameWithoutExtension, file.readLines()))
        }
    }

    override fun onExit() {
        // TODO
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

            // Validation
            val keyName = "$category.$varName"
            if (validations.containsKey(keyName)) {
                if (varType != validations[keyName]!!.clazz)
                    logger().throwException("Config $keyName is not valid! (Type requested: ${validations[keyName]})", ClassCastException(),
                        "Config", XH_STATUS_GENERAL_ERROR)
            }

            // Property type

            if (varValue.contains("env(")) {
                val params = varValue.substring(4, varValue.lastIndexOf(")"))
                // Todo
            }

            when (varType) {
                "String" -> list[keyName] = varValue.substring(1, varValue.length-1)
                "Int" -> list[keyName] = varValue.toInt()
                "Float" -> list[keyName] = varValue.toFloat()
                "Boolean" -> list[keyName] = varValue.toBoolean()
            }
        }

        logger().internal_log(list.toString(), LogLevel.CONFIG, "Config")

        return list
    }
}

fun config(name: String): Any? = Config[name]
fun validateConfigs(list: Map<String, PropertyType>) = Config.validations.putAll(list)

enum class PropertyType(internal val clazz: String) {
    STRING("String"),
    INT("Int"),
    FLOAT("Float"),
    BOOLEAN("Boolean")
}