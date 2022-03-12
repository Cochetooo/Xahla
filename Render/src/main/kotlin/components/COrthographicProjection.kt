package components

import Priority
import objects.OrthographicCamera
import org.joml.Matrix4f
import org.lwjgl.opengl.GL11.*

@Priority(level=PriorityLevel.HIGHEST)
class COrthographicProjection(obj: OrthographicCamera, private val size: FloatArray) : CProjection(obj) {

    private var left = size[0]
    private var right = size[1]
    private var bottom = size[2]
    private var top = size[3]

    init {
        this.contextDimension = "2d"
        this.projection = Matrix4f().ortho2D(left, right, bottom, top)
    }

    override fun onRender() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glDisable(GL_DEPTH_TEST)

        super.onRender()
    }

}