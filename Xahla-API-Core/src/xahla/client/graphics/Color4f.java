package xahla.client.graphics;

import org.joml.Vector3i;
import org.joml.Vector4i;

/**
 * Simple color class that contains a red, green, blue and alpha value, each between 0 and 1 (0 to 255)
 * 
 * @author Cochetooo
 * @version 1.3
 */
public class Color4f {
	
	/** RGB(1,1,1) #FFF */
	public static final Color4f WHITE = new Color4f(1);
	/** RGB(0.75,0.75,0.75) #c0c0c0 */
	public static final Color4f DARK_GRAY = new Color4f(0.75f);
	/** RGB(0.5,0.5,0.5) #808080 */
	public static final Color4f GRAY = new Color4f(0.5f);
	/** RGB(0.25,0.25,0.25) #404040 */
	public static final Color4f LIGHT_GRAY = new Color4f(0.25f);
	/** RGB(0,0,0) #000 */
	public static final Color4f BLACK = new Color4f(0);
	
	/** RGB(1,0,0) #F00 */
	public static final Color4f RED = 	new Color4f(1, 0, 0);
	/** RGB(0,1,0) #0F0 */
	public static final Color4f GREEN = new Color4f(0, 1, 0);
	/** RGB(0,0,1) #00F */
	public static final Color4f BLUE = 	new Color4f(0, 0, 1);
	
	/** The proportion of red in the color. */   public float red;
	/** The proportion of green in the color. */ public float green;
	/** The proportion of blue in the color. */	 public float blue; 
	/** The opacity of the color. */			 public float alpha;
	
	/**
	 * Initialize a new gray-shaded color.
	 * @param c The gray shade.
	 */
	public Color4f(float c) { 					this(c, c, c); 			}
	/**
	 * Initialize a rgb color.
	 * @param r The level of red.
	 * @param g The level of green.
	 * @param b The level of blue.
	 */
	public Color4f(float r, float g, float b) { this(r, g, b, 1.0f); 	}
	/**
	 * Initialize a rgba color.
	 * @param r The level of red.
	 * @param g The level of green.
	 * @param b The level of blue.
	 * @param a The opacity.
	 */
	public Color4f(float r, float g, float b, float a) {
		this.red = Math.max(0.0f, Math.min(1.0f, r));
		this.green = Math.max(0.0f, Math.min(1.0f, g));
		this.blue = Math.max(0.0f, Math.min(1.0f, b));
		this.alpha = Math.max(0.0f, Math.min(1.0f, a));
	}
	
	/**
	 * @param v The vector that contains the RGB integer values (between 0 and 255).
	 * @return	A color with values between 0 and 1.
	 */
	public Color4f map(Vector3i v) { return map(v.x, v.y, v.z); }
	/**
	 * @param r	The red integer value.
	 * @param g The green integer value.
	 * @param b The blue integer value.
	 * @return  A color with values between 0 and 1.
	 */
	public Color4f map(int r, int g, int b) { return new Color4f(r / 255.f, g / 255.f, b / 255f); }
	
	/** @return A hexadecimal integer containing the RGB values. */
	public int toRGB() {
		Vector3i colorVec = asRGBVector();
		return (colorVec.x << 16) + (colorVec.y << 8) + colorVec.z;
	}
	
	/** @return A hexadecimal integer containing the RGBA values. */
	public int toRGBA() {
		Vector4i colorVec = asRGBAVector();
		return (colorVec.x << 24) + (colorVec.y << 16) + (colorVec.z << 8) + colorVec.w;
	}
	
	/** @return A vector containing the RGB values. */
	public Vector3i asRGBVector() {
		return new Vector3i((int) (red * 255.f), (int) (green * 255.f), (int) (blue * 255.f));
	}
	
	/** @return A vector containing the RGBA values. */
	public Vector4i asRGBAVector() {
		return new Vector4i((int) (red * 255.f), (int) (green * 255.f), (int) (blue * 255.f), (int) (alpha * 255.f));
	}

}
