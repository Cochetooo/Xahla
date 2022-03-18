import org.joml.Vector2f
import org.joml.Vector3f

object MapCollision {
    /** Test if an object is set at a specific position in a 2D world. */
    fun collideAt2D(position: Vector2f): Boolean {


        return false
    }
    /** Test if an object is set at a specific position in a 3D world. */
    fun collideAt3D(position: Vector3f): Boolean {
        return false
    }
}

fun collideAt(position: Vector3f): Boolean = MapCollision.collideAt3D(position)
fun collideAt(position: Vector2f): Boolean = MapCollision.collideAt2D(position)