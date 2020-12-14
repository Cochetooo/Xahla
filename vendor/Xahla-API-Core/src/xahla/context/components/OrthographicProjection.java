package xahla.context.components;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;

import xahla.context.objects.EntityObject;
import xahla.core.ObjectPriority;
import xahla.core.Priority;

/**
 * Give a linear 2D orthographic projection to the projection matrix.
 * 
 * @author Cochetooo
 * @version 1.1
 */
@ObjectPriority(priority=Priority.HIGH)
public class OrthographicProjection extends Projection {
	
	public float left, right, bottom, top;

	/**
	 * @param object	The Entity Object to work for.
	 * @param size		The scale of the projection (often set to 0, width, height, 0):
	 * <ul>
	 * 	<li>Left value</li>
	 * 	<li>Right value</li>
	 *  <li>Bottom value</li>
	 *  <li>Top value</li>
	 * </ul>
	 */
	public OrthographicProjection(EntityObject object, float[] size) {
		super(object, "Projection");
		
		this.left = size[0];
		this.right = size[1];
		this.bottom = size[2];
		this.top = size[3];
		
		this.contextDimension = "2D";
		
		resize();
	}
	
	@Override
	public void resize() {
		this.projection = new Matrix4f().ortho2D(left, right, bottom, top);
	}
	
	/** Disable depth (3D) and clear color buffer. */
	@Override
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glDisable(GL_DEPTH_TEST);
		
		super.render();
	}
}
