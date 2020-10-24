package xahla.context.components;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL15;

import xahla.client.graphics.Shader;
import xahla.client.graphics.Texture;
import xahla.client.graphics.VertexArrayObject;
import xahla.context.objects.Camera;
import xahla.context.objects.EntityObject;
import xahla.core.Component;

/**
 * A Mesh Renderer handles a Vertex Array Object to render an Entity Object.<br>
 * It can store a texture.<br>
 * <b>For the moment, it only allows position and texture coordinates data.</b><br><br>
 * 
 * {@link xahla.client.graphics.VertexArrayObject}
 * {@link xahla.context.objects.EntityObject}
 * @author Cochetooo
 * @version 1.0
 */
public class MeshRenderer extends Component {
	
	private Texture texture;
	private Shader shader;
	private VertexArrayObject vao;
	private int drawMode;

	/**
	 * Create a Mesh Rendered without texture.
	 * @param object	The Entity Object to work for.
	 * @param shader	The shader (GLSL) binded to the VAO.
	 * @param drawMode	OpenGL Draw Mode (usually <b>GL_TRIANGLES</b> or <b>GL_QUADS</b>.
	 */
	public MeshRenderer(EntityObject object, Shader shader, int drawMode) { this(object, shader, drawMode, null); }
	/**
	 * @param object	The Entity Object to work for.
	 * @param shader	The shader (GLSL) binded to the VAO.
	 * @param drawMode	OpenGL Draw Mode (usually <b>GL_TRIANGLES</b> or <b>GL_QUADS</b>.
	 * @param tex		The texture of the mesh.
	 */
	public MeshRenderer(EntityObject object, Shader shader, int drawMode, Texture tex) {
		super(object, "MeshRenderer");
		
		this.texture = tex;
		this.drawMode = drawMode;
		
		this.shader = shader;
		this.vao = new VertexArrayObject(shader, 3, 0, 0, 2, drawMode, data());
	}

	@Override
	public void init() {
		
	}

	/** Updates the data. */
	@Override
	public void update() {
		if (drawMode == GL15.GL_DYNAMIC_DRAW || drawMode == GL15.GL_STREAM_DRAW)
			vao.subData(data());;
	}
	
	/** Bind the texture (if not null), then the shader, put the projection matrix and eventually render the mesh. */
	@Override
	public void render() {
		if (texture != null)
			texture.bind();
		
		this.shader.bind();
		shader.loadMat(shader.getUniformLocation("projectionMatrix"), ((Camera) this.getObjectToWorkFor().getContext().getObjectByName("MainCamera")).projection().getProjection());
		
		vao.render();
		
		Shader.unbind();
		if (texture != null)
			Texture.unbind();
		
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		vao.dispose();
	}
	
	@Override protected EntityObject getObjectToWorkFor() { return (EntityObject) super.getObjectToWorkFor(); }
	
	private float[] data() {
		Vector3f pos = this.getObjectToWorkFor().transform().getPosition();
		Vector3f bounds = this.getObjectToWorkFor().collider().getBounds();
		
		return new float[] {
			pos.x, pos.y, 0,
			0, 0,
			
			pos.x + bounds.x, pos.y, 0,
			1, 0,
			
			pos.x + bounds.x, pos.y + bounds.y, 0,
			1, 1,
			
			pos.x, pos.y + bounds.y, 0,
			0, 1
		};
	}
}
