package components

import Component
import Priority
import config
import objects.Entity
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryStack.stackPush

@Priority(level=PriorityLevel.HIGHEST)
abstract class CProjection(obj: Entity) : Component(obj) {

    var projection: Matrix4f = Matrix4f()
        protected set
    var contextDimension = config("window.projection") as String
        protected set

    init {
        obj.transform.rotation.set(Quaternionf().lookAlong(obj.getPos(), Vector3f()))
    }

    override fun onRender() {
        val trans = (obj as Entity).transform

        if (config("window.engine") == "gl") {
            glMatrixMode(GL_PROJECTION)

            stackPush().use {
                val fb = it.mallocFloat(16)
                projection[fb]
                glLoadMatrixf(fb)
            }

            glMatrixMode(GL_MODELVIEW)
            glLoadIdentity()

            glPushAttrib(GL_TRANSFORM_BIT)
            glRotatef(0.0f, 1.0f, 0.0f, 0.0f)
            glRotatef(0.0f, 0.0f, 1.0f, 0.0f)
            glTranslatef(-trans.position.x, -trans.position.y, -trans.position.z)
            glPopAttrib()
        }
    }

}
