package xahla.context.components;

/**
 * A collider is used to detect collision between two objects.<br>
 * This interface implements the collide method.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public interface ICollider {
	
	/**
	 * @param pObj	The other collider which is used for collision detection.
	 * @return		True if the two objects collides.
	 */
	public boolean collide(ICollider pObj);

}
