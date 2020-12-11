package xahla.context.components;

import org.joml.Vector3f;

import xahla.client.graphics.Shader;
import xahla.client.graphics.Texture;
import xahla.context.objects.EntityObject;

/**
 * This component is used for rectangle mesh creation.
 * 
 * {@link xahla.client.graphics.VertexArrayObject}
 * {@link xahla.context.objects.EntityObject}
 * @author Cochetooo
 * @version 1.0
 */
public final class RectRenderer extends MeshRenderer {
	
	static {
		length = 4;
	}
	
	/**
	 * Create a rectangle mesh rendered without texture nor shader.
	 * @param object	The Entity Object to work for.
	 * @param name		The Name of the Component.
	 * @param drawMode	VAO Draw Mode (usually <b>GL_DYNAMIC_DRAW</b>, <b>GL_STREAM_DRAW</b> or <b>GL_STATIC_DRAW</b>.
	 */
	public RectRenderer(EntityObject object, int drawMode) { this(object, drawMode, null, null); }
	
	/**
	 * Create a rectangle mesh rendered without specific shader.
	 * @param object	The Entity Object to work for.
	 * @param name		The Name of the Component.
	 * @param drawMode	VAO Draw Mode (usually <b>GL_DYNAMIC_DRAW</b>, <b>GL_STREAM_DRAW</b> or <b>GL_STATIC_DRAW</b>.
	 * @param shader	The shader (GLSL) binded to the VAO.
	 */
	public RectRenderer(EntityObject object, int drawMode, Shader shader) { this(object, drawMode, shader, null); }
	
	/**
	 * Create a rectangle mesh rendered without texture.
	 * @param object	The Entity Object to work for.
	 * @param name		The Name of the Component.
	 * @param drawMode	VAO Draw Mode (usually <b>GL_DYNAMIC_DRAW</b>, <b>GL_STREAM_DRAW</b> or <b>GL_STATIC_DRAW</b>.
	 * @param tex		The texture of the mesh.
	 */
	public RectRenderer(EntityObject object, int drawMode, Texture tex) { this(object, drawMode, null, tex); }
	
	/**
	 * @param object	The Entity Object to work for.
	 * @param name		The Name of the Component.
	 * @param drawMode	VAO Draw Mode (usually <b>GL_DYNAMIC_DRAW</b>, <b>GL_STREAM_DRAW</b> or <b>GL_STATIC_DRAW</b>.
	 * @param shader	The shader (GLSL) binded to the VAO.
	 * @param tex		The texture of the mesh.
	 */
	public RectRenderer(EntityObject object, int drawMode, Shader shader, Texture tex) {
		super(object, "RectRenderer", drawMode, shader, tex);
	}
	
	@Override
	protected float[] data() {
		Vector3f pos = this.getObjectToWorkFor().pos();
		Vector3f bounds = this.getObjectToWorkFor().collider().getBounds();
		
		int size = posLength + colorLength + texLength + normalLength;
		
		float[] result = new float[size * length];
		
		for (int i = 0; i < length-1; i++) {
			result[size * i] = pos.x + (i == 1 || i == 2 ? bounds.x : 0);
			result[size * i + 1] = pos.y + (i == 2 || i == 3 ? bounds.y : 0);
			
			if (posLength > 2)
				result[size * i + 2] = pos.z;
			
			if (colorLength > 0) {
				result[size * i + posLength] = color[i].red;
				result[size * i + posLength + 1] = color[i].green;
				result[size * i + posLength + 2] = color[i].blue;
				result[size * i + posLength + 3] = color[i].alpha;
			}
			
			if (texLength > 0) {
				result[size * i + posLength + colorLength] = offset.x + (i == 1 || i == 2 ? stride.x : 0);
				result[size * i + posLength + colorLength + 1] = offset.y + (i == 2 || i == 3 ? stride.y : 0);
			}
			
			if (normalLength > 0) {
				result[size * i + posLength + colorLength + texLength] = normals[i].x;
				result[size * i + posLength + colorLength + texLength + 1] = normals[i].y;
				
				if (normalLength > 2)
					result[size * i + posLength + colorLength + texLength + 2] = normals[i].z;
			}
		}
		
		return result;
	}

}
