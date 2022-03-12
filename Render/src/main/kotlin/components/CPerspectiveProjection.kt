package components

import Priority
import clientContext
import objects.PerspectiveCamera
import org.joml.Matrix4f
import org.joml.Vector2i
import org.lwjgl.opengl.GL11.*

@Priority(level=PriorityLevel.HIGHEST)
class CPerspectiveProjection(obj: PerspectiveCamera, private val params: FloatArray) : CProjection(obj) {

    var fov = params[0]
    var zNear = params[1]
    var zFar = params[2]
    var aspect = 1.0f
        private set

    init {
        this.contextDimension = "3d"

        updateProjection()
    }

    private fun updateProjection() {
        val dim: Vector2i = clientContext().window.dimension
        this.aspect = dim.x as Float / dim.y as Float
        this.projection = Matrix4f().perspective(fov, aspect, zNear, zFar)
    }

    override fun onUpdate() {
        super.onUpdate()
        updateProjection()
    }

    override fun onRender() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glEnable(GL_DEPTH_TEST)

        super.onRender()
    }

}