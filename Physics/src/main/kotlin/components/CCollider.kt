package components

import Component
import objects.Entity

abstract class CCollider(obj: Entity) : Component(obj) {

    /** Test if current object will be colliding another object after his movement in the upcoming frame in a 2D space. */
    abstract fun collideAABB2D(other: CCollider): Boolean
    /** Test if current object will be colliding another object after his movement in the upcoming in a 3D space. */
    abstract fun collideAABB3D(other: CCollider): Boolean

}