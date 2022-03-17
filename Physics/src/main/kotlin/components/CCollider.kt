package components

import Component
import objects.Entity

abstract class CCollider(obj: Entity) : Component(obj) {

    abstract fun collide2D(other: CCollider): Boolean
    abstract fun collide3D(other: CCollider): Boolean

}