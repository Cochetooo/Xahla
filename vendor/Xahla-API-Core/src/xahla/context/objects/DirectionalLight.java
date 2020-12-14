package xahla.context.objects;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL15;

import xahla.client.graphics.Texture;
import xahla.context.components.RectRenderer;

/**
 * A directional light emits a light within the world.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public class DirectionalLight extends EntityObject {
	
	private float strength;

	/**
	 * @param name		The name of the Directional Light.
	 * @param Context	The context of the program.
	 * @param strength	The strength of the light diffusion.
	 * @param tex		The texture of the light diffuser.
	 */
	public DirectionalLight(String name, xahla.core.Context Context, float strength, Texture tex) {
		super(name, Context);
		
		this.strength = strength;
		this.add(new RectRenderer(this, GL15.GL_DYNAMIC_DRAW, tex)
			.setDefaultTexCoords());
	}
	
	/** @return The center of the light diffusion. By default located at the center of the object. */
	public Vector3f getLightPosition() { return new Vector3f(this.pos()).add(new Vector3f(this.bounds()).div(2)); }
	
	/** @return The strength of the light diffusion. */
	public float getStrength() { return this.strength; }

}
