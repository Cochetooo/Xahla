import templates.XH_ILogic
import templates.XH_IObjectLogic
import java.awt.Component
import java.util.stream.Collectors

/** Basic Object of the Engine
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
open class XH_Object(val name: String = "XH_Object") : XH_IObjectLogic, Comparable<XH_Object> {
    companion object {
        private var auto_increment = 0
    }

    var destroyed = false
        private set
    val id = (auto_increment++)

    val components: MutableList<XH_Component> = mutableListOf()

    fun destroy() {
        destroyed = true
    }

    protected fun add(newComponent: XH_Component) {
        components.add(newComponent)
    }

    protected fun set(name: String, newComponent: XH_Component): Boolean {
        for (i in 0 until components.size) {
            if (components[i].name == name) {
                components[i] = newComponent
                return true
            }
        }

        return false
    }

    fun find(name: String): XH_Component?
        = components.stream().filter { it.name == name }.findFirst().orElse(null)

    fun find(id: Int): XH_Component?
            = components.stream().filter { it.id == id }.findFirst().orElse(null)

    fun get(name: String): List<XH_Component>
            = components.stream().filter { it.name == name }.collect(Collectors.toList())

    fun get(clazz: Class<out XH_Component>): List<XH_Component>
            = components.stream().filter { it.javaClass == clazz }.collect(Collectors.toList())

    fun contains(comp: String): Boolean
        = components.stream().anyMatch { it.name == comp }

    fun contains(comp: Int): Boolean
            = components.stream().anyMatch { it.id == comp }

    fun contains(comp: Class<out XH_Component>): Boolean
            = components.stream().anyMatch { comp.isAssignableFrom(it.javaClass) }

    override fun compareTo(other: XH_Object): Int {
        return this.id - other.id
    }

    override fun onInit() = components.forEach { it.onInit() }
    override fun onUpdate() = components.forEach { it.onUpdate() }
    override fun onPostUpdate() = components.forEach { it.onPostUpdate() }
    override fun onSecond() = components.forEach { it.onSecond() }
    override fun onDispose() = components.forEach { it.onDispose() }

    override fun toString(): String {
        var result = "Object $name [${this.javaClass.simpleName}] ID: $id\n"
        for (c in components)
            result += "\tComponent $c.name [${c.javaClass.simpleName}] ID: ${c.id}\n"
        return result
    }

}