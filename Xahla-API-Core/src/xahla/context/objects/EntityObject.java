package xahla.context.objects;

import xahla.context.components.BoxCollider;
import xahla.context.components.Transform;
import xahla.core.Context;
import xahla.core.XObject;

/**
 * An Entity Object is represented on the world with a transform containing his position, rotation and scale.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public class EntityObject extends XObject {
	
	private boolean visible;
	
	/**
	 * @param name		The name.
	 * @param Context	The context of the program.
	 */
	public EntityObject(String name, Context Context) {
		super(name, Context);
		
		this.visible = true;
		
		this.add(new Transform(this));
		this.add(new BoxCollider(this));
	}
	
	/** <i>If the object is not visible, it will not draw.</i> */
	@Override
	public void render() {
		if (!visible) return;
		super.render();
	}
	
	/** @return The container of the object position, rotation and scale. */
	public Transform transform() { return (Transform) this.getComponent("Transform"); }
	/** @return The collider. */
	public BoxCollider collider() { return (BoxCollider) this.getComponent("BoxCollider"); }
	
	/** @return True if the object is allowed to render in the world. */
	public boolean isVisible() { return visible; }
	/** @param b Change visibility of the object. */
	public void setVisibility(boolean b) { this.visible = b; }

}
