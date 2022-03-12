package objects

import XHR_SCREEN_DIMENSION
import clientContext
import config
import graphics.opengl.gl

class OrthographicCamera : Entity() {

    init {
        val posData = (config("orthographic.position") as String).split("-")
        val orthoData = (config("orthographic.ortho") as String).split("-")

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
    }

}