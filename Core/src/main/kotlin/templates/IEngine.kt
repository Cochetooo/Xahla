/** Basic Logic methods of the engine
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
package templates

interface IEngine {
    /** Called after the internal engine initialization, while creating the context. */
    fun onInit()    {}

    /** Called on each cycles of the program execution. */
    fun onUpdate()      {}
    /** Called on each cycles of the program execution, at the very end of the cycle. */
    fun onPostUpdate()  {}

    fun onRender() {}
    fun onPostRender() {}

    fun onResize() {}

    /** Called each seconds. */
    fun onSecond()      {}

    /** Called when the program got a request to terminate its execution, before the program exits. */
    fun onDispose()     {}
}