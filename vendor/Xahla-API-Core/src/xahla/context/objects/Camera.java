package xahla.context.objects;

import org.joml.Vector3f;

import xahla.context.ClientContext;
import xahla.context.components.Projection;
import xahla.context.components.Transform;
import xahla.core.ObjectPriority;
import xahla.core.Priority;

/**
 * The Camera is an Entity Object that contains a projection to render the world.
 * 
 * @author Cochetooo
 * @version 1.0
 */
@ObjectPriority(priority=Priority.HIGH)
public abstract class Camera extends EntityObject {

	/**
	 * Add the camera with a position set to the center of the world.
	 * @param name		The name of the Camera.
	 * @param Context	The context of the program as a Client Context.
	 */
	public Camera(String name, ClientContext Context) { this(name, new Vector3f(), Context); }
	/**
	 * @param name		The name of the Camera.
	 * @param pos		The position of the Camera.
	 * @param Context	The context of the program as a Client Context.
	 */
	public Camera(String name, Vector3f pos, ClientContext Context) {
		super(name, Context);
		this.set("Transform", new Transform(this, pos));
	}
	
	/** @return The context of the program as a Client Context. */
	@Override public ClientContext getContext() { return (ClientContext) super.getContext(); }
	/** @return The projection of the camera. */
	public Projection projection() { return (Projection) this.getComponent("Projection"); }

}
