package objects

import UseComponent
import XH_Object
import components.CTransform
import org.joml.Quaternionf
import org.joml.Vector3f

open class Entity(position: Vector3f = Vector3f(), rotation: Quaternionf = Quaternionf(), scale: Vector3f = Vector3f()) : XH_Object() {

    var visible: Boolean = true
    var detached: Boolean = false

    @UseComponent val transform = CTransform(this)

    init {
        transform.position.set(position)
        transform.rotation.set(rotation)
        transform.scale.set(scale)
    }

    override fun onRender() {
        if (!visible) return
        super.onRender()
    }

    fun getPos(): Vector3f = this.transform.position
    fun getRot(): Quaternionf = this.transform.rotation
    fun getScale(): Vector3f = this.transform.scale

}