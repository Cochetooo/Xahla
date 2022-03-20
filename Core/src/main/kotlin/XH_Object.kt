import templates.IEngine
import utils.XH_STATUS_ENGINE_ERROR
import utils.logger
import java.util.stream.Collectors

/** Basic Object of the Engine
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
open class XH_Object
        @JvmOverloads constructor(val name: String = "XH_Object") : IEngine, Comparable<XH_Object> {

    companion object {
        private var auto_increment = 0
    }

    var destroyed = false
        private set
    val id = (auto_increment++)

    val components: MutableList<Component> = mutableListOf()
    private val compNames: MutableList<String> = mutableListOf()

    private val priority: PriorityLevel
        get() {
            for (annotation in this::class.java.annotations) {
                if (annotation is Priority)
                    return annotation.level
            }

            return PriorityLevel.NORMAL
        }

    fun destroy() {
        destroyed = true
    }

    private fun add(newComponent: Component) {
        components.add(newComponent)
        compNames.add(newComponent.javaClass.simpleName)
    }

    protected operator fun set(name: String, newComponent: Component): Boolean {
        for (i in 0 until components.size) {
            if (components[i].name == name) {
                components[i] = newComponent
                return true
            }
        }

        return false
    }

    fun find(name: String): Component?
        = components.stream().filter { it.name == name }.findFirst().orElse(null)

    fun find(id: Int): Component?
            = components.stream().filter { it.id == id }.findFirst().orElse(null)

    fun has(className: String): Boolean
        = compNames.contains(className)

    operator fun get(name: String): List<Component>
            = components.stream().filter { it.name == name }.collect(Collectors.toList())

    operator fun get(clazz: Class<out Component>): List<Component>
            = components.stream().filter { it.javaClass == clazz }.collect(Collectors.toList())

    fun contains(comp: String): Boolean
        = components.stream().anyMatch { it.name == comp }

    fun contains(comp: Int): Boolean
            = components.stream().anyMatch { it.id == comp }

    fun contains(comp: Class<out Component>): Boolean
            = components.stream().anyMatch { comp.isAssignableFrom(it.javaClass) }

    override fun compareTo(other: XH_Object): Int {
        return this.priority.ordinal - other.priority.ordinal
    }

    private fun addComponentsByField(cls: Class<out Any>) {
        for (c in cls.declaredFields) {
            if (cls.superclass != null) addComponentsByField(cls.superclass)

            if (c.getAnnotation(UseComponent::class.java) != null) {
                c.trySetAccessible()
                val result = c[this]

                if (result !is Component)
                    logger().throwException("@UseComponent annotation can only be set on Component fields!", RuntimeException(), classSource = "XH_Object", statusCode = XH_STATUS_ENGINE_ERROR)

                this.add(result)
            }
        }
    }

    /* IENGINE INTERFACE */

    override fun onAwake() = components.forEach { it.onAwake() }

    override fun onInit() {
        addComponentsByField(this.javaClass)

        components.forEach { it.onInit() }
    }

    override fun onUpdate() = components.forEach { it.onUpdate() }
    override fun onPostUpdate() = components.forEach { it.onPostUpdate() }
    override fun onRender() = components.forEach { it.onRender() }
    override fun onPostRender() = components.forEach { it.onPostRender() }
    override fun onResize() = components.forEach { it.onResize() }
    override fun onSecond() = components.forEach { it.onSecond() }
    override fun onPause() = components.forEach { it.onPause() }
    override fun onResume() = components.forEach { it.onResume() }
    override fun onDispose() = components.forEach { it.onDispose() }
    override fun onExit() = components.forEach { it.onExit() }

    override fun toString(): String {
        var result = "=============================\n" +
                "$name [${this.javaClass.simpleName}] ID: $id\n"
        for (c in components)
            result += "\tComponent $c.name [${c.javaClass.simpleName}] ID: ${c.id}\n"
        result += "============================="
        return result
    }

}