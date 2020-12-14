package xahla.context.components;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import xahla.client.input.Input;
import xahla.context.objects.EntityObject;
import xahla.core.Component;

/**
 * This component handles player movement for a top view.<br>
 * It does not take gravity into account but handles drag.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public class BasicController extends Component {
	
	private int left, right, up, down;
	private Vector2f velocity, deltaPos, drag;
	
	/**
	 * @param object	The Entity Object to work for.
	 * @param velocity	The speed that is applied to the movement.
	 */
	public BasicController(EntityObject object, Vector2f velocity) { this(object, velocity, new Vector2f()); }
	/**
	 * @param object	The Entity Object to work for.
	 * @param velocity	The speed that is applied to the movement.
	 * @param drag		The slip rate.
	 */
	public BasicController(EntityObject object, Vector2f velocity, Vector2f drag) {
		super(object, "BasicController");
		
		this.velocity = velocity;
		this.drag = drag;
		
		this.deltaPos = new Vector2f();
		
		String l = this.getContext().getConfigString("Input", "left_movement");
		String r = this.getContext().getConfigString("Input", "right_movement");
		String u = this.getContext().getConfigString("Input", "up_movement");
		String d = this.getContext().getConfigString("Input", "down_movement");
		
		try {
			left =  GLFW.class.getField("GLFW_KEY_" + l.toUpperCase()).getInt(null);
			right = GLFW.class.getField("GLFW_KEY_" + r.toUpperCase()).getInt(null);
			up = 	GLFW.class.getField("GLFW_KEY_" + u.toUpperCase()).getInt(null);
			down = 	GLFW.class.getField("GLFW_KEY_" + d.toUpperCase()).getInt(null);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			this.getObjectToWorkFor().getContext().getExceptionHandler().reportException(e, "The requested key does not exist (input.json)");;
		}
	}

	@Override
	public void init() {
		
	}

	/**
	 * Apply a movement whenever a binded movement input is pressed,<br>
	 * then translate the entity's position.<br><br>
	 * Apply some slipping if the drag factor is not 0.
	 */
	@Override
	public void update() {
		if (Input.keyDown(left))
			deltaPos.x = -velocity.x;
		if (Input.keyDown(right))
			deltaPos.x =  velocity.x;
		if (Input.keyDown(up))
			deltaPos.y = -velocity.y;
		if (Input.keyDown(down))
			deltaPos.y =  velocity.y;
		
		this.getObjectToWorkFor().transform().translate(deltaPos.x, deltaPos.y, 0);
		
		deltaPos.mul(drag);
	}
	
	/** @return The speed that is applied to the movement. */
	public Vector2f getVelocity() { return velocity; }
	/** @param f The new speed. */
	public void setVelocity(Vector2f f) { this.velocity = f; }
	
	/** @return The slip rate. */
	public Vector2f getDrag() { return drag; }
	/** @param f The new drag force. */
	public void setDrag(Vector2f f) { this.drag = f; }
	
	@Override protected EntityObject getObjectToWorkFor() { return (EntityObject) super.getObjectToWorkFor(); }
}
