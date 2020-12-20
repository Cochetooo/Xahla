package xahla.context.objects;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

import xahla.client.graphics.Graphics;
import xahla.context.ClientContext;
import xahla.context.components.OrthographicProjection;
import xahla.core.ObjectPriority;
import xahla.core.Priority;

/**
 * The Orthographic Camera contains an orthographic projection for 2D rendering.
 * 
 * @author Cochetooo
 * @version 1.1
 */
@ObjectPriority(priority=Priority.HIGH)
public class OrthographicCamera extends Camera {
	
	private OrthographicProjection proj;

	/**
	 * Add an Orthographic Camera to the Context and initialize it with the Orthographic configuration (orthographic.json).
	 * @param name		The name of the Camera. (the one by default is "MainCamera").
	 * @param Context	The context of the program as a Client Context.
	 */
	public OrthographicCamera(String name, ClientContext Context) {
		super(name, Context);
		
		String[] posData = Context.getConfigString("Orthographic", "position").split("-");
		float[] ortho = retrieveOrthoData();
		
		this.transform().setLocalPosition(new Vector3f(Float.parseFloat(posData[0]), Float.parseFloat(posData[1]), 0));
		proj = new OrthographicProjection(this, ortho);
		this.add(proj);
	}
	
	/**
	 * @param name		The name of the Camera. (the one by default is "MainCamera").
	 * @param Context	The context of the program as a Client Context.
	 * @param pos		The position of the Camera in the world.
	 * @param size		The size of the projection (top, left, right, bottom).
	 */
	public OrthographicCamera(String name, ClientContext Context, Vector2f pos, float[] size) {
		super(name, new Vector3f(pos.x, pos.y, 0), Context);
		this.add(new OrthographicProjection(this, size));
	}
	
	@Override
	public void resize() {
		float[] ortho = retrieveOrthoData();
		proj.left = ortho[0];
		proj.right = ortho[1];
		proj.bottom = ortho[2];
		proj.top = ortho[3];
		
		super.resize();
	}
	
	public float[] retrieveOrthoData() {
		String[] orthoData = this.getContext().getConfigString("Orthographic", "ortho").split("-");
		
		Vector2i dim = this.getContext().getWindow().getWindowDimension();
		float[] ortho = new float[4];
		
		for (int i = 0; i < 3; i++) {
			if (orthoData[i].equals("width"))
				ortho[i] = dim.x;
			else if (orthoData[i].equals("height"))
				ortho[i] = dim.y;
			else if (orthoData[i].equals("auto")) {
				switch (i) {
				case 0, 3 -> ortho[i] = 0;
				case 1 -> ortho[i] = Graphics.screenDimension().x;
				case 2 -> ortho[i] = Graphics.screenDimension().y;
				}
			} else {
				ortho[i] = Float.parseFloat(orthoData[i]);
			}
		}
		
		return ortho;
	}
	
	/** @return The Projection Matrix calculated in the Orthographic Projection component. */
	public Matrix4f projectionMatrix() { return ((OrthographicProjection) this.getComponent("OrthographicProjection")).getProjection(); }

}