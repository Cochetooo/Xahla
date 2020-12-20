package xahla.context.components;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL15;

import xahla.client.graphics.Color4f;
import xahla.client.graphics.Shader;
import xahla.client.graphics.Texture;
import xahla.client.graphics.objects.VertexArrayObject;
import xahla.context.ClientContext;
import xahla.context.objects.Camera;
import xahla.context.objects.EntityObject;
import xahla.core.Component;
import xahla.utils.ValidationException;

/**
 * A Mesh Renderer handles a Vertex Array Object to render an Entity Object.<br>
 * It can store a texture.<br>
 * <b>For the moment, it only allows position, normal, color and texture coordinates data.</b><br><br>
 * 
 * {@link xahla.client.graphics.objects.VertexArrayObject}
 * {@link xahla.context.objects.EntityObject}
 * @author Cochetooo
 * @version 1.2
 */
public abstract class MeshRenderer extends Component {
	
	protected Texture texture;
	protected Shader shader;
	protected VertexArrayObject vao;
	
	protected Vector2f offset, stride;
	protected Vector3f[] normals;
	protected Color4f[] color;
	
	protected int vertices;
	
	protected static int length;
	
	protected int drawMode;
	
	protected int posLength, normalLength, texLength, colorLength;
	
	protected float[] data;
	protected boolean repeatUpdate;
	
	/**
	 * Create a Mesh rendered without texture nor shader.
	 * @param object	The Entity Object to work for.
	 * @param name		The Name of the Component.
	 * @param drawMode	VAO Draw Mode (usually <b>GL_DYNAMIC_DRAW</b>, <b>GL_STREAM_DRAW</b> or <b>GL_STATIC_DRAW</b>.
	 */
	protected MeshRenderer(EntityObject object, String name, int drawMode) { this(object, name, drawMode, null, null); }
	
	/**
	 * Create a Mesh rendered without specific shader.
	 * @param object	The Entity Object to work for.
	 * @param name		The Name of the Component.
	 * @param drawMode	VAO Draw Mode (usually <b>GL_DYNAMIC_DRAW</b>, <b>GL_STREAM_DRAW</b> or <b>GL_STATIC_DRAW</b>.
	 * @param shader	The shader (GLSL) binded to the VAO.
	 */
	protected MeshRenderer(EntityObject object, String name, int drawMode, Shader shader) { this(object, name, drawMode, shader, null); }
	
	/**
	 * Create a Mesh rendered without texture.
	 * @param object	The Entity Object to work for.
	 * @param name		The Name of the Component.
	 * @param drawMode	VAO Draw Mode (usually <b>GL_DYNAMIC_DRAW</b>, <b>GL_STREAM_DRAW</b> or <b>GL_STATIC_DRAW</b>.
	 * @param tex		The texture of the mesh.
	 */
	protected MeshRenderer(EntityObject object, String name, int drawMode, Texture tex) { this(object, name, drawMode, null, tex); }
	
	/**
	 * @param object	The Entity Object to work for.
	 * @param name		The Name of the Component.
	 * @param drawMode	VAO Draw Mode (usually <b>GL_DYNAMIC_DRAW</b>, <b>GL_STREAM_DRAW</b> or <b>GL_STATIC_DRAW</b>.
	 * @param shader	The shader (GLSL) binded to the VAO.
	 * @param tex		The texture of the mesh.
	 */
	protected MeshRenderer(EntityObject object, String name, int drawMode, Shader shader, Texture tex) {
		super(object, name);
		
		this.drawMode = drawMode;
		this.shader = shader;
		this.texture = tex;
		
		posLength = (object.getContext().getConfigString("Rendering", "projection").equals("3d")) ? 3 : 2;
		texLength = 0;
		colorLength = 0;
		normalLength = 0;
	}
	
	/**
	 * @param tex	The texture of the mesh.
	 */
	public MeshRenderer setTexture(Texture tex) {
		this.texture = tex;
		return this;
	}
	
	/**
	 * Set a different color for each vertices of the mesh. This will produce a radient effect on the mesh.
	 * @param color	An array of color that must contains the same amount of colors than the mesh's number of vertices.
	 */
	public MeshRenderer setColors(Color4f[] color) {
		if (color.length != length)
			throw new ValidationException("Color array size must be " + length + ".");
		
		this.color = color;
		colorLength = 4;
		
		return this;
	}
	
	/**
	 * @param color	The color of the mesh. It will apply for each vertices of the object.
	 */
	public MeshRenderer setColor(Color4f color) {
		this.color = new Color4f[length];		
		colorLength = 4;
		
		for (int i = 0; i < length; i++)
			this.color[i] = color;
		
		return this;
	}
	
	/**
	 * Texture coordinates range from 0 to 1 in the <b>x</b> and <b>y</b> axis.<br>
	 * @param offset The starting point (usually left corner) of the sampled texture.
	 * @param stride The bounds of the sampled texture.
	 */
	public MeshRenderer setTextureCoordinates(Vector2f offset, Vector2f stride) {
		this.offset = offset;
		this.stride = stride;
		
		this.texLength = 2;
		
		return this;
	}
	
	/**
	 * Set the texture coordinates to the default zoom of the texture (it will render the whole texture).
	 */
	public MeshRenderer setDefaultTexCoords() {
		return setTextureCoordinates(new Vector2f(0, 0), new Vector2f(1, 1));
	}
	
	/**
	 * Normals are a unit vector whose direction is perpendicular to a surface at a specific point.
	 * @param normals	An array of normals vector for the mesh that must contains the same amount of vector than the mesh's number of vertices.
	 */
	public MeshRenderer setNormals(Vector3f[] normals) {
		if (normals.length != length)
			throw new ValidationException("Normals array size must be " + length + ".");
			
		this.normals = normals;
		normalLength = (this.getContext().getConfigString("Rendering", "projection").equals("3d")) ? 3 : 2;
		
		return this;
	}
	
	/**
	 * Enable normals and set it to a default square normals.
	 */
	public MeshRenderer setDefaultNormals() {
		this.normals = new Vector3f[4];
		//Vector3f bounds = this.getObjectToWorkFor().collider().getBounds();
		
//		this.normals[0] = new Vector3f(0, -bounds.x, 0);
//		this.normals[1] = new Vector3f(bounds.y, 0, 0);
//		this.normals[2] = new Vector3f(0, bounds.x, 0);
//		this.normals[3] = new Vector3f(-bounds.y, 0, 0);
		
		this.normals[0] = new Vector3f(0, -1, 0);
		this.normals[1] = new Vector3f(1, 0, 0);
		this.normals[2] = new Vector3f(0, 1, 0);
		this.normals[3] = new Vector3f(-1, 0, 0);
		
		normalLength = (this.getContext().getConfigString("Rendering", "projection").equals("3d")) ? 3 : 2;
		
		return this;
	}
	
	/**
	 * Enable constant update for vertex buffer data.<br>
	 * It can cost some performance.
	 */
	public MeshRenderer enableRepeatedUpdate() {
		this.repeatUpdate = true;
		
		return this;
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void post_init() {
		this.data = data();
		this.vao = new VertexArrayObject(
			(shader == null) ? this.getContext().getWorldShader() : shader, 
			posLength, colorLength, normalLength, texLength, 
			drawMode, 
			length,
			data
		);
	}
	
	/**
	 * If repeat update is enabled, will update the vertex buffer's data.
	 */
	@Override
	public void update() {
		if (repeatUpdate)
			if (drawMode == GL15.GL_DYNAMIC_DRAW || drawMode == GL15.GL_STREAM_DRAW)
				this.applyChanges();
	}
	
	/**
	 * Render the mesh by binding its shader, as well as its texture if it has one, then render the vertex array object that stores the mesh.
	 */
	@Override
	public void render() {
		if (texture != null)
			texture.bind();
		
		if (shader != null) {
			shader.bind();
			shader.loadMat(shader.getUniformLocation("projectionMatrix"), ((Camera) this.getObjectToWorkFor().getContext().getObjectByName("MainCamera")).projection().getProjection());
		}
		
		vao.render();
		
		if (shader != null)
			Shader.unbind();
		
		if (texture != null)
			Texture.unbind();
	}
	
	@Override
	public void dispose() {
		vao.dispose();
	}
	
	/**
	 * Update buffer data for the mesh (will update the rendering).
	 */
	public void applyChanges() {
		this.data = data();
		vao.subData(data);
	}
	
	protected abstract float[] data();
	
	/** @return The texture of the mesh. */
	public Texture getTexture() { return texture; }
	
	@Override protected EntityObject getObjectToWorkFor() { return (EntityObject) super.getObjectToWorkFor(); }
	
	@Override public ClientContext getContext() { return (ClientContext) super.getContext(); }
}
