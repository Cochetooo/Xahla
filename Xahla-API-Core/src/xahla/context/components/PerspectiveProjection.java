package xahla.context.components;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector2i;

import xahla.context.objects.EntityObject;
import xahla.core.ObjectPriority;
import xahla.core.Priority;

/**
 * Give a 3D perspective projection to the projection matrix.
 * 
 * @author Cochetooo
 * @version 1.1
 */
@ObjectPriority(priority=Priority.HIGH)
public class PerspectiveProjection extends Projection {
	
	/**
	 * @param object	The Entity Object to work for.
	 * @param params	The different projection parameters:
	 * <ul>
	 * 	<li>The Field of View (FOV)</li>
	 *  <li>The nearest rendered distance (zNear)</li>
	 *  <li>The farthest rendered distance (zFar)</li>
	 * </ul>
	 */
	public PerspectiveProjection(EntityObject object, float[] params) {
		super(object, "Projection");
		this.fov = params[0];
		this.zNear = params[1];
		this.zFar = params[2];
		
		this.contextDimension = "3D";
		
		updateProjection();
	}
	
	private void updateProjection() {
		Vector2i dim = this.getObjectToWorkFor().getContext().getWindow().getWindowDimension();
		this.aspect = (float) dim.x / (float) dim.y;
		this.projection = new Matrix4f().perspective(fov, aspect, zNear, zFar);
	}
	
	/** Update the aspect ratio of the window and the projection. */
	@Override
	public void update() {
		super.update();
		
		updateProjection();
	}
	
	/** Enable depth (3D) and clear color and depth buffers. */
	@Override
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glEnable(GL_DEPTH_TEST);
		
		super.render();
	}
}
