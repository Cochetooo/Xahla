import org.json.JSONException
import org.json.JSONObject
import templates.ICoreEngine
import utils.*
import java.io.File
import java.math.BigDecimal
import java.net.URLClassLoader

/** Configuration Handling
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
object XH_Config : ICoreEngine {

    private val properties: MutableMap<String, Any> = mutableMapOf()

    operator fun get(key: String): Any? = properties[key]
    operator fun set(key: String, value: Any) {
        properties[key] = value
    }

    override fun onAwake() {
        File("configs").walk().forEach { file ->
            if (file.name == "configs") return@forEach


            val content = file.bufferedReader().use { it.readText() }

            xh_tryCatch ({
                val jsonObject = JSONObject(content)
                val keys = jsonObject.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    val obj = jsonObject.get(key)
                    if (obj is BigDecimal) {
                        properties["${file.nameWithoutExtension}.$key"] = obj.toFloat()
                    } else if (obj is String || obj is Number || obj is Boolean) {
                        properties["${file.nameWithoutExtension}.$key"] = obj
                    } else {
                        logger().throwException("The value of $key in ${file.name} has an unvalid format.",
                            IllegalArgumentException(), classSource="XH_Config", statusCode = XH_STATUS_JSON_ERROR)
                    }
                }
            }, catchException = JSONException::class.java)
        }
    }

    override fun onExit() {

    }
}

fun config(name: String): Any? = XH_Config[name]