import templates.ICoreEngine
import utils.LogLevel
import utils.Logger
import utils.XH_STATUS_GENERAL_ERROR
import utils.logger
import java.util.stream.Collectors
import kotlin.IllegalStateException

/** Context of the program
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
open class Context(private val app: App) : ICoreEngine {

    val objects: MutableList<XH_Object> = mutableListOf()

    private val validations = mapOf("app.appName" to String::class.java,
        "app.appVersion" to String::class.java,
        "app.appAuthor" to String::class.java,
        "app.appEnvironment" to String::class.java,
        "app.updatePerSecond" to Integer::class.java,
        "app.framePerSecond" to Integer::class.java,
        "debugger.internalLogging" to java.lang.Boolean::class.java,
        "debugger.logExceptionFile" to java.lang.Boolean::class.java,
        "debugger.logLevel" to String::class.java,
        "debugger.prefix" to java.lang.Boolean::class.java
    )

    override fun onAwake() {
        Config.onAwake()
        Logger.onAwake()

        validateConfigs(validations)
    }

    override fun onUpdate() {
        val iter = objects.iterator()
        while (iter.hasNext()) {
            val obj = iter.next()
            if (obj.destroyed) {
                iter.remove()
                continue
            }

            obj.onUpdate()
        }
    }
    override fun onPostUpdate() = objects.forEach { it.onPostUpdate() }
    override fun onSecond() = objects.forEach { it.onSecond() }
    override fun onDispose() {
        objects.forEach { it.onDispose() }
    }

    /**
     * Add an object to the program.
     */
    fun add(obj: XH_Object) {
        obj.onInit()
        objects.add(obj)
    }

    fun remove(obj: XH_Object) {
        obj.onDestroy()
        objects.remove(obj)
    }

    fun getObjectsByClass(objClass: Class<out XH_Object>): List<XH_Object>
            = objects.stream().filter { objClass.isInstance(it) }.collect(Collectors.toList())

    fun validateConfigs(properties: Map<String, Class<out Any>>) {
        for (property in properties) {
            try {
                logger().internal_log("Validator: ${property.key} = " + config(property.key)?.javaClass!!.typeName, LogLevel.FINEST, "Context")
                property.value.cast(config(property.key))
            } catch (cce: ClassCastException) {
                logger().throwException("Config ${property.key} is not a valid ${property.value.name}.", cce, "Context", XH_STATUS_GENERAL_ERROR)
            } catch (npe: NullPointerException) {
                logger().throwException("Config ${property.key} is not found.", npe, "Context", XH_STATUS_GENERAL_ERROR)
            }
        }
    }
}