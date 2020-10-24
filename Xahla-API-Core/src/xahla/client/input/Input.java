package xahla.client.input;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

/**
 * This class handles GLFW keyboard and mouse input.<br>
 * 
 * @author Cochetooo
 * @version 1.0
 */
public class Input {
	
	private static long window;
	private static final int KEYBOARD_SIZE = 512;
	private static final int MOUSE_SIZE = 16;
	
	private static int[] keyStates = new int[KEYBOARD_SIZE];
	private static boolean[] activeKeys = new boolean[KEYBOARD_SIZE];
	
	private static int[] mouseButtonStates = new int[MOUSE_SIZE];
	private static boolean[] activeMouseButtons = new boolean[MOUSE_SIZE];
	
	private static long lastMouseNS = 0;
	private static long mouseDoubleClickPeriodNS = 1_000_000_000 / 5;
	
	private static int NO_STATE = -1;
	
	/**
	 * Initialize the Input class (similarly to a constructor but for a static class).
	 * @param _window	The window ID.
	 */
	public static void init(long _window) {
		window = _window;
		
		resetKeyboard();
		resetMouse();
	}
	
	/** The keyboard callback handle. */
	public static GLFWKeyCallback keyboard = new GLFWKeyCallback() {
		@Override
		public void invoke(long win, int key, int scancode, int action, int mods) {
			if (win != window) return;
			
			activeKeys[key] = action != GLFW_RELEASE;
			keyStates[key] = action;
		}
	};
	
	/** The mouse callback handle. */
	public static GLFWMouseButtonCallback mouse = new GLFWMouseButtonCallback() {
		@Override
		public void invoke(long win, int button, int action, int mods) {
			if (win != window) return;
			
			activeMouseButtons[button] = action != GLFW_RELEASE;
			mouseButtonStates[button] = action;
		}
	};
	
	/** Reset the keyboard and mouse states, then poll GLFW events. */
	public static void update() {
		resetKeyboard();
		resetMouse();
		
		glfwPollEvents();
	}
	
	private static void resetKeyboard() {
		for (int i = 0; i < keyStates.length; i++)
			keyStates[i] = NO_STATE;
	}
	
	private static void resetMouse() {
		for (int i = 0; i < mouseButtonStates.length; i++)
			mouseButtonStates[i] = NO_STATE;
		
		long now = System.nanoTime();
		
		if (now - lastMouseNS > mouseDoubleClickPeriodNS)
			lastMouseNS = 0;
	}
	
	/**
	 * @param key	The key code (check GLFW.GLFW_KEY_*).
	 * @return		True if the key is currently pressed.
	 */
	public static boolean keyDown(int key) { return activeKeys[key]; }
	/**
	 * @param key	The key code (check GLFW.GLFW_KEY_*).
	 * @return		True if the key was just pressed.
	 */
	public static boolean keyPressed(int key) { return keyStates[key] == GLFW_PRESS; }
	/**
	 * @param key	The key code (check GLFW.GLFW_KEY_*).
	 * @return		True if the key was just released.
	 */
	public static boolean keyReleased(int key) { return keyStates[key] == GLFW_RELEASE; }
	
	/**
	 * @param button	The mouse button ID.<b><br><ul>
	 * 					<li>0 = Left</li>
	 * 					<li>1 = Right</li>
	 * 					<li>2 = Middle</li>
	 * 					<li>3 and more = Extra buttons</li>
	 * 					</ul></b>
	 * @return			True if the mouse button is currently clicked.
	 */
	public static boolean mouseButtonDown(int button) { return activeMouseButtons[button]; }
	/**
	 * @param button	The mouse button ID.<b><br><ul>
	 * 					<li>0 = Left</li>
	 * 					<li>1 = Right</li>
	 * 					<li>2 = Middle</li>
	 * 					<li>3 and more = Extra buttons</li>
	 * 					</ul></b>
	 * @return			True if the mouse button was just pressed.
	 */
	public static boolean mouseButtonPressed(int button) { return mouseButtonStates[button] == GLFW_PRESS; }
	/**
	 * @param button	The mouse button ID.<b><br><ul>
	 * 					<li>0 = Left</li>
	 * 					<li>1 = Right</li>
	 * 					<li>2 = Middle</li>
	 * 					<li>3 and more = Extra buttons</li>
	 * 					</ul></b>
	 * @return			True if the mouse button was just released.
	 */
	public static boolean mouseButtonReleased(int button) { 
		boolean flag = mouseButtonStates[button] == GLFW_RELEASE;
		
		if (flag)
			lastMouseNS = System.nanoTime();
		
		return flag;
	}
	
	/**
	 * @param button	The mouse button ID.<b><br><ul>
	 * 					<li>0 = Left</li>
	 * 					<li>1 = Right</li>
	 * 					<li>2 = Middle</li>
	 * 					<li>3 and more = Extra buttons</li>
	 * 					</ul></b>
	 * @return			True if the button has been clicked twice within a certain delay.
	 */
	public static boolean mouseButtonDoubleClicked(int button) {
		long last = lastMouseNS;
		boolean flag = mouseButtonReleased(button);
		
		long now = System.nanoTime();
		
		if (flag && now - last < mouseDoubleClickPeriodNS) {
			lastMouseNS = 0;
			return true;
		}
		
		return false;
	}

}
