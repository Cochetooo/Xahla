package xahla.context.components;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

import xahla.context.objects.Camera;
import xahla.context.objects.EntityObject;
import xahla.core.Component;
import xahla.core.ObjectPriority;
import xahla.core.Priority;

/**
 * A Basic projection class that can be inherited from different projections in 2D or 3D.
 * 
 * {@link xahla.context.components.OrthographicProjection}
 * {@link xahla.context.components.PerspectiveProjection}
 * @author Cochetooo
 * @version 1.2
 */
@ObjectPriority(priority=Priority.HIGH)
public class Projection extends Component {
	
	protected Matrix4f projection;
	protected float fov, aspect, zFar, zNear;
	protected String contextDimension;
	
	/**
	 * Make the projection look at (0,0,0).
	 * @param object	The Entity Object to work for.
	 * @param name		The name of the projection.
	 */
	public Projection(EntityObject object, String name) {
		super(object, name);
		
		this.getObjectToWorkFor().transform().setLocalRotation(new Quaternionf().lookAlong(this.getObjectToWorkFor().transform().getPosition(), new Vector3f()));
	}
	
	/** Update the projection matrix. */
	@Override
	public void render() {
		Transform trans = this.getObjectToWorkFor().transform();
		
		glMatrixMode(GL_PROJECTION);
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer fb = stack.mallocFloat(16);
			projection.get(fb);
			glLoadMatrixf(fb);
		}
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		glPushAttrib(GL_TRANSFORM_BIT);
		glRotatef(new Vector3f().x, 1, 0, 0);
		glRotatef(new Vector3f().y, 0, 1, 0);
		glTranslatef(-trans.getPosition().x, -trans.getPosition().y, -trans.getPosition().z);
		glPopAttrib();
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		
	}
	
	@Override
	protected final Camera getObjectToWorkFor() { return (Camera) super.getObjectToWorkFor(); }
	
	/** @return The projection dimension <i>(usually 2D or 3D)</i>. */
	public final String getContextDimension() { return contextDimension; }
	
	/** @return The projection matrix */
	public final Matrix4f getProjection() { 
//		Transform trans = this.getObjectToWorkFor().transform();
//		
//		Matrix4f translationMatrix = new Matrix4f().translate(new Vector3f(trans.getPosition()).negate());
//		Matrix4f rotationMatrix = new Matrix4f().rotate(trans.getRotation());
//		Matrix4f scaleMatrix = new Matrix4f().scale(trans.getScale());
//		
//		return projection.mul(scaleMatrix.mul(rotationMatrix.mul(translationMatrix))); 
		
		return projection;
	}

}
