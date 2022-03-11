import templates.ICoreEngine
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
open class Context(private val app: XH_App) : ICoreEngine {

    val objects: MutableList<XH_Object> = mutableListOf()

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

    override fun onAwake() {
        if (config("app.updatePerSecond") == null)
            logger().throwException("configs/app.updatePerSecond is not found! Please restore app.kt.", IllegalStateException(),
                classSource = "Context", statusCode = XH_STATUS_GENERAL_ERROR)
        else {
            if (config("app.updatePerSecond") !is Int)
                logger().throwException("configs/app.updatePerSecond must be an integer.", IllegalStateException(),
                    classSource = "Context", statusCode = XH_STATUS_GENERAL_ERROR)
        }
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

}