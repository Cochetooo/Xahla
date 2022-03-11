import templates.IEngine

annotation class UseComponent

/** Components for program objects
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
open class Component
        @JvmOverloads constructor(val obj: XH_Object, val name: String = "XH_Component") : IEngine, Comparable<Component> {

    companion object {
        private var auto_increment = 0
    }

    val id = (auto_increment++)

    override fun compareTo(other: Component): Int {
        return this.id - other.id
    }
}