import objects.Entity
import templates.IEngine
import utils.Logger
import java.util.stream.Collectors

/** Context of the program
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
open class Context(private val app: App) : IEngine {

    val objects: MutableList<XH_Object> = mutableListOf()
    val entities: MutableList<Entity> = mutableListOf()

    override fun onAwake() {
        Config.onAwake()
        Logger.onAwake()
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
    override fun onRender() = objects.forEach { it.onRender() }
    override fun onPostRender() = objects.forEach { it.onPostRender() }
    override fun onResize() = objects.forEach { it.onResize() }
    override fun onSecond() = objects.forEach { it.onSecond() }
    override fun onPause() = objects.forEach { it.onPause() }
    override fun onResume() = objects.forEach { it.onResume() }
    override fun onDispose() = objects.forEach { it.onDispose() }
    override fun onExit() = objects.forEach { it.onExit() }

    /**
     * Add an object to the program.
     */
    fun add(obj: XH_Object) {
        obj.onAwake()
        obj.onInit()
        objects.add(obj)
        if (obj is Entity)
            entities.add(obj)
    }

    fun remove(obj: XH_Object) {
        obj.onDispose()
        objects.remove(obj)
        if (obj is Entity)
            entities.remove(obj)
    }

    fun objectsWith(objClass: Class<out XH_Object>): List<XH_Object>
            = objects.stream().filter { objClass.isInstance(it) }.collect(Collectors.toList())

    operator fun get(name: String): XH_Object = objects.stream().filter { it.name == name }.findFirst().orElse(null)
}