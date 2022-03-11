package objects

import UseComponent
import XH_Object
import components.CTransform
import org.joml.Quaternionf
import org.joml.Vector3f

class Entity : XH_Object() {

    var visible: Boolean = true
    var detached: Boolean = false

    @UseComponent val transform = CTransform(this)

    override fun onRender() {
        if (!visible) return
        super.onRender()
    }

    fun getPos(): Vector3f = this.transform.position
    fun getRot(): Quaternionf = this.transform.rotation
    fun getScale(): Vector3f = this.transform.scale

}