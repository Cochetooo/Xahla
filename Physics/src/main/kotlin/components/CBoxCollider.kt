package components

import objects.Entity
import org.joml.Vector3f

class CBoxCollider(obj: Entity) : CCollider(obj) {

    val box = Vector3f()

    override fun collide2D(other: CCollider): Boolean {
        val a = obj as Entity
        val b = other.obj as Entity

        when (other) {
            is CBoxCollider -> {
                if (a.transform.position.x + this.box.x > b.transform.position.x
                    && a.transform.position.y + this.box.y > b.transform.position.y
                    && a.transform.position.x < b.transform.position.x + other.box.x
                    && a.transform.position.y < b.transform.position.y + other.box.y)
                    return true
            }
        }

        return false
    }

    override fun collide3D(other: CCollider): Boolean {
        val a = obj as Entity
        val b = other.obj as Entity

        when (other) {
            is CBoxCollider -> {
                if (a.transform.position.x + this.box.x > b.transform.position.x
                    && a.transform.position.y + this.box.y > b.transform.position.y
                    && a.transform.position.z + this.box.z > b.transform.position.z
                    && a.transform.position.x < b.transform.position.x + other.box.x
                    && a.transform.position.y < b.transform.position.y + other.box.y
                    && a.transform.position.z < b.transform.position.z + other.box.z)
                    return true
            }
        }

        return false
    }

}