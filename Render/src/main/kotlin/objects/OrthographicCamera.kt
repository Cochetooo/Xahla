package objects

import UseComponent
import XHR_SCREEN_DIMENSION
import clientContext
import components.COrthographicProjection
import config
import org.joml.Vector3f

class OrthographicCamera(name: String) : Entity(name) {

    @UseComponent val projection: COrthographicProjection

    init {
        val posData = (config("gl.position") as String).split("-")
        val orthoData = (config("gl.ortho") as String).split("-")

        val ortho = FloatArray(4)
        val dim = clientContext().window.dimension

        for (i in 0 until 4) {
            if (orthoData[i] == "width")
                ortho[i] = dim.x.toFloat()
            else if (orthoData[i] == "height")
                ortho[i] = dim.y.toFloat()
            else if (orthoData[i] == "auto") {
                when (i) {
                    0, 3    -> ortho[i] = 0.0f
                    1       -> ortho[i] = XHR_SCREEN_DIMENSION.x
                    2       -> ortho[i] = XHR_SCREEN_DIMENSION.y
                }
            } else {
                ortho[i] = orthoData[i].toFloat()
            }
        }

        this.detached = true
        this.projection = COrthographicProjection(this, ortho)
        this.transform.position.set(Vector3f(posData[0].toFloat(), posData[1].toFloat(), 0.0f))
    }

}