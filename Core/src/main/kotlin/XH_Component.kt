import templates.XH_ILogic

/** Components for program objects
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
open class XH_Component(val obj: XH_Object, val name: String = "XH_Component") : XH_ILogic, Comparable<XH_Component> {
    companion object {
        private var auto_increment = 0
    }

    val id = (auto_increment++)

    override fun compareTo(other: XH_Component): Int {
        return this.id - other.id
    }
}