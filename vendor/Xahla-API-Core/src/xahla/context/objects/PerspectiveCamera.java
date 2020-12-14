package xahla.context.objects;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import xahla.context.ClientContext;
import xahla.context.components.PerspectiveProjection;
import xahla.core.ObjectPriority;
import xahla.core.Priority;

/**
 * The Perspective Camera contains a perspective projection for 3D rendering.
 * 
 * @author Cochetooo
 * @version 1.1
 */
@ObjectPriority(priority=Priority.HIGH)
public class PerspectiveCamera extends Camera {
	
	/**
	 * Add a Perspective Camera to the Context and initialize it with the Perspective configuration (perspective.json).
	 * @param name		The name of the Camera. (the one by default is "MainCamera").
	 * @param Context	The context of the program as a Client Context.
	 */
	public PerspectiveCamera(String name, ClientContext Context) {
		super(name, Context);
		String[] posData = Context.getConfigString("Perspective", "position").split("-");
		
		this.transform().setLocalPosition(new Vector3f(Float.parseFloat(posData[0]), Float.parseFloat(posData[1]), Float.parseFloat(posData[2])));
		this.transform().setLocalRotation(new Quaternionf());
		this.add(new PerspectiveProjection(this, new float[] {
			Context.getConfigFloat("Perspective", "fov"),
			Context.getConfigFloat("Perspective", "zNear"),
			Context.getConfigFloat("Perspective", "zFar")
		}));
	}
	
	/**
	 * @param name		The name of the Camera. (the one by default is "MainCamera").
	 * @param Context	The context of the program as a Client Context.
	 * @param pos		The position of the Camera in the world.
	 * @param params	The projection parameters (fov, zNear, zFar).
	 */
	public PerspectiveCamera(String name, ClientContext Context, Vector3f pos, float[] params) {
		super(name, pos, Context);
		this.add(new PerspectiveProjection(this, params));
	}
	
	/** @return The Projection Matrix calculated in the Perspective Projection component. */
	public Matrix4f projectionMatrix() { return ((PerspectiveProjection) this.getComponent("PerspectiveProjection")).getProjection(); }

}
