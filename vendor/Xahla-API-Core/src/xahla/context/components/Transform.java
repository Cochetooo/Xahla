package xahla.context.components;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import xahla.core.Component;
import xahla.core.XObject;

/**
 * A Transform is a component that contains data about how an object is represented in the world.<br>
 * It contains a position <i>(where it is)</i>, a rotation <i>(where it looks at)</i> and a scale <i>(how big it is)</i>.
 * 
 * @author Cochetooo
 * @version 1.1
 */
public class Transform extends Component {
	
	private Vector3f position, scale;
	private Quaternionf rotation;

	/**
	 * Generate a new Transform at (0,0,0), with a rotation of 0° and a normal scale (1,1,1).
	 * @param object	The Object to work for.
	 */
	public Transform(XObject object) { this(object, new Vector3f()); }
	/**
	 * Generate a new Transform with a rotation of 0° and a normal scale (1,1,1).
	 * @param object	The Object to work for.
	 * @param pos		The location of the object in the world.
	 */
	public Transform(XObject object, Vector3f pos) { this(object, pos, new Quaternionf()); }
	/**
	 * Generate a new Transform with a normal scale (1,1,1).
	 * @param object	The Object to work for.
	 * @param pos		The location of the object in the world.
	 * @param rot		The rotation of the object.
	 */
	public Transform(XObject object, Vector3f pos, Quaternionf rot) { this(object, pos, rot, new Vector3f()); }
	/**
	 * @param object	The Object to work for.
	 * @param pos		The location of the object in the world.
	 * @param rot		The rotation of the object.
	 * @param scale		The scale of the object.
	 */
	public Transform(XObject object, Vector3f pos, Quaternionf rot, Vector3f scale) {
		super(object, "Transform");
		
		this.position = pos;
		this.scale = scale;
		
		this.rotation = rot;
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void update() {
		
	}
	
	/** @return The transform data into a float matrix 4x4. */
	public Matrix4f toMatrix() {
		Matrix4f translationMatrix = new Matrix4f().translate(position);
		Matrix4f rotationMatrix = new Matrix4f().rotate(rotation);
		Matrix4f scaleMatrix = new Matrix4f().scale(scale);
		Matrix4f parentMatrix = new Matrix4f();
		
		Matrix4f transformationMatrix = parentMatrix.mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
		
		return transformationMatrix;
	}
	
	/** Rotate the object. */
	public void rotate(Vector3f axis, float angle) {
		rotation = new Quaternionf().fromAxisAngleDeg(axis, angle).mul(rotation).normalize();
	}
	
	/** Translate, rotate and scale the object by the transform of another object. */
	public void add(Transform pTrans) {
		position.add(pTrans.position);
		rotation.add(pTrans.rotation);
		scale.add(pTrans.scale);
	}
	
	/**
	 * Translate the object.
	 * @param pos	The direction vector.
	 */
	public void translate(Vector3f pos) { translate(pos.x, pos.y, pos.z); }
	/**
	 * Translate the object.
	 * @param x		The x-axis direction.
	 * @param y		The y-axis direction.
	 * @param z		The z-axis direction.
	 */
	public void translate(float x, float y, float z) {
		position.add(x, y, z);
	}
	
	/** @return The position of the object. */
	public final Vector3f getPosition() { return position; }
	/** @return The scale of the object. */
	public final Vector3f getScale() { return scale; }
	/** @return The rotation of the object. */
	public final Quaternionf getRotation() { return rotation; }
	
	/** @return The relative position of the object (change of the world position if it inherits from another object). */
	public final Vector3f getLocalPosition() { return position; }
	/**
	 * Translate the object position.
	 * @param newP	The new location.
	 */
	public void setLocalPosition(Vector3f newP) { this.position = newP; }
	
	/** @return The relative scale of the object (change of the world scale if it inherits from another object). */
	public final Vector3f getLocalScale() { return scale; }
	/**
	 * Scale the object.
	 * @param newP	The new scale.
	 */
	public void setLocalScale(Vector3f newP) { this.scale = newP; }
	
	/** @return The relative rotation of the object (change of the world rotation if it inherits from another object). */
	public final Quaternionf getLocalRotation() { return rotation; }
	/**
	 * Rotate the object.
	 * @param newP	The new rotation.
	 */
	public void setLocalRotation(Quaternionf newP) { this.rotation = newP; }

}
