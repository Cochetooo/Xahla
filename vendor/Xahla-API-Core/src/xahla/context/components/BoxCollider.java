package xahla.context.components;

import org.joml.Vector3f;

import xahla.context.objects.EntityObject;
import xahla.core.Component;

/**
 * A Box Collider generates a size for the EntityObject to handle collision detection.<br>
 * The size is box shaped.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public class BoxCollider extends Component implements ICollider {
	
	private Vector3f bounds, position;

	/**
	 * Generate a box collider with a size of 0.
	 * @param object	The Entity Object to work for.
	 */
	public BoxCollider(EntityObject object) { this(object, new Vector3f()); }
	/**
	 * @param object	The Entity Object to work for.
	 * @param bounds	The size of the box.
	 */
	public BoxCollider(EntityObject object, Vector3f bounds) {
		super(object, "BoxCollider");
		
		this.bounds = bounds;
		this.position = object.transform().getLocalPosition();
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		
	}
	
	@Override
	public boolean collide(ICollider pObj) {
		if (pObj instanceof BoxCollider pBox)
			return pBox.position.x + pBox.bounds.x >= position.x && pBox.position.x <= position.x + bounds.x
				&& pBox.position.y + pBox.bounds.y >= position.y && pBox.position.y <= position.y + bounds.y
				&& pBox.position.z + pBox.bounds.z >= position.z && pBox.position.z <= position.z + bounds.z;
				
		return false;
	}
	
	/** @return The size of the box. */
	public Vector3f getBounds() { return bounds; }

}
