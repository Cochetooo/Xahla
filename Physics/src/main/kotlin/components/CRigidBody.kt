package components

import Component
import objects.Entity
import utils.LogLevel
import utils.logger

class CRigidBody(obj: Entity) : Component(obj) {

    private val collider: CCollider

    init {
        val comp = obj[CCollider::class.java].firstOrNull()
        if (comp == null)
            logger().internal_log("No collider found for Rigidbody on object #${obj.id} ${obj.name}", LogLevel.WARNING, "CRigidBody")

        collider = comp as CCollider
    }

}