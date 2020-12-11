package xahla.context.objects;

import org.joml.Quaternionf;
import org.joml.Vector3f;

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
	private boolean detached;
	
	/**
	 * @param name		The name of the Object.
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
	
	/** @return Quick accessor for the transform position. */
	public Vector3f pos() { return this.transform().getPosition(); }
	/** @return Quick accessor for the transform rotation. */
	public Quaternionf rot() { return this.transform().getRotation(); }
	/** @return Quick accessor for the transform scale. */
	public Vector3f scale() { return this.transform().getScale(); }
	
	/** @return Quick accessor for the size. */
	public Vector3f bounds() { return this.collider().getBounds(); }
	
	/** @return The container of the object position, rotation and scale. */
	public Transform transform() { return (Transform) this.getComponent("Transform"); }
	/** @return The collider. */
	public BoxCollider collider() { return (BoxCollider) this.getComponent("BoxCollider"); }
	
	/** @return True if the object is allowed to render in the world. */
	public boolean isVisible() { return visible; }
	/** Set the object visible. */
	public void hide() { this.visible = false; }
	/** Hide the object from the world. */
	public void show() { this.visible = true; }
	
	/** 
	 * Detach the object from the world shader.<br>
	 * The object will need its own shader to render properly.
	 */
	public void detach() { this.detached = true; }
	/** Attach the object with the world shader. */
	public void attach() { this.detached = false; }
	/** @return True if the object is detached from the world shader. */
	public boolean isDetached() { return this.detached; }

}
