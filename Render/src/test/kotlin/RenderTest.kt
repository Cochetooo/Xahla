import graphics.opengl.GLTexture
import graphics.opengl.loadTexture
import graphics.opengl.unbindTextures
import input.input
import objects.Entity
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*
import templates.IEngine
import utils.logger

fun main() {
    Main()
}

class Main : IEngine {
    init {
        logger().excepts = arrayOf()
        app().build(ClientContext::class.java, this)
        app().run()

        // Prohibited to do something here
    }

    override fun onInit() {
        super.onInit()
        context().add(Maki())
    }

    override fun onResize() {
        logger().log("RESIZE")
    }

    override fun onSecond() {
        clientContext().window.setWindowTitle("${config("window.title")} | UPS: ${app().ups} FPS: ${app().fps}")
    }

}

internal class Maki : Entity("Maki") {
    var texture: GLTexture? = null

    override fun onInit() {
        super.onInit()
        this.transform.position.set(Vector3f(50.0f, 50.0f, 0.0f))
        this.detached = true
        texture = loadTexture("maki harukawa.jpg")
    }

    override fun onUpdate() {
        super.onUpdate()
        if (input().keyDown(GLFW_KEY_A))
            transform.position.x -= 1.5f
        if (input().keyDown(GLFW_KEY_D))
            transform.position.x += 1.5f
        if (input().keyDown(GLFW_KEY_W))
            transform.position.y -= 1.5f
        if (input().keyDown(GLFW_KEY_S))
            transform.position.y += 1.5f
    }

    override fun onRender() {
        texture!!.bind()
        glBegin(GL_QUADS)
            glTexCoord2i(0, 0)
            glVertex2f(this.transform.position.x, this.transform.position.y)
            glTexCoord2i(1, 0)
            glVertex2f(this.transform.position.x + 400, this.transform.position.y)
            glTexCoord2i(1, 1)
            glVertex2f(this.transform.position.x + 400, this.transform.position.y + 400)
            glTexCoord2i(0, 1)
            glVertex2f(this.transform.position.x, this.transform.position.y + 400)
        glEnd()
        unbindTextures()
    }

}