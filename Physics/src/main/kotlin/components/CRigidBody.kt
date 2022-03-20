package components

import Component
import objects.Entity
import org.joml.Vector3f
import utils.LogLevel
import utils.logger

class CRigidBody(obj: Entity) : Component(obj) {

    private val collider: CCollider

    var solid: Boolean = true
    var mass: Float = 1.0f
    var kinetic: Boolean = true

    init {
        val comp = obj[CCollider::class.java].firstOrNull()
        if (comp == null)
            logger().internal_log("No collider found for Rigidbody on object #${obj.id} ${obj.name}", LogLevel.WARNING, "CRigidBody")

        collider = comp as CCollider
    }

    fun move(direction: Vector3f) {
        if (solid) {

        }

        if (kinetic)
            moveWithTorque(direction)
        else
            (obj as Entity).transform.position.add(direction)
    }

    private fun moveWithTorque(direction: Vector3f) {

    }

}