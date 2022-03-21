import org.joml.Vector2f
import org.joml.Vector2i
import org.joml.Vector3f
import utils.LogLevel
import utils.logger

object MapCollision {
    /** Test if an object is set at a specific position in a 2D world. */
    fun collideAt2D(position: Vector2i): Boolean {
        if (!Physics.useCollisionGrid) {
            logger().internal_log("Trying to call collideAt2D() when collision grid is not set.",
                LogLevel.WARNING, "MapCollision")

            return false
        }

        if (Physics.collisionGrid!![position.x][position.y][Physics.defaultZLayerFor2D] != 0)
            return true

        return false
    }
    /** Test if an object is set at a specific position in a 3D world. */
    fun collideAt3D(position: Vector3f): Boolean {
        if (!Physics.useCollisionGrid) {
            logger().internal_log("Trying to call collideAt3D() when collision grid is not set.",
                LogLevel.WARNING, "MapCollision")

            return false
        }

        return false
    }
}

fun collideAt(position: Vector3f): Boolean = MapCollision.collideAt3D(position)
fun collideAt(position: Vector2f): Boolean = MapCollision.collideAt2D(position)