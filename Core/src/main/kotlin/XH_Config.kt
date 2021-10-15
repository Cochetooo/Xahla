import org.json.JSONException
import org.json.JSONObject
import templates.XH_ICoreLogic
import templates.XH_ILogic
import utils.XH_FRAMEWORK_ROOT
import utils.XH_Logger
import utils.XH_STATUS_JSON_ERROR
import utils.xh_tryCatch
import java.io.File
import java.util.*

/** Configuration Handling
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
object XH_Config : XH_ICoreLogic {

    val properties: MutableMap<String, Any> = mutableMapOf()

    operator fun get(key: String): Any = properties[key] ?: "null"
    operator fun set(key: String, value: Any) {
        properties[key] = value
    }

    override fun onAwake() {
        File("$XH_FRAMEWORK_ROOT/configs/").walk().forEach { file ->
            val content = file.bufferedReader().use { it.readText() }

            xh_tryCatch ({
                val jsonObject = JSONObject(content)
                val keys = jsonObject.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    val obj = jsonObject.get(key)
                    if (obj is String || obj is Number) {
                        properties["${file.nameWithoutExtension}.$key"] = obj
                    } else {
                        XH_Logger.throwException("The value of $key in ${file.name} has an unvalid format.")
                    }
                }
            }, catchException = JSONException::class.java)
        }
    }

    override fun onExit() {

    }
}

fun config(): XH_Config = XH_Config