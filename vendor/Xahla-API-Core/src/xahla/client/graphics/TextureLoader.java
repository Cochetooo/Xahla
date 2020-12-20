package xahla.client.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_LOD_BIAS;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

import xahla.client.graphics.objects.FrameBufferObject;

/**
 * TextureLoader is a utility class for Texture. It allows efficient Texture creation.
 * 
 * @author Cochetooo
 * @version 1.3
 */
public class TextureLoader {
	
	private static Map<String, Texture> cache = new HashMap<>();
	private static List<Integer> textures = new ArrayList<>();
	
	/**
	 * Generate a texture ID.
	 * @return The Texture ID.
	 */
	public static int createTexture() {
		int texture = glGenTextures();
		textures.add(texture);
		
		return texture;
	}
	
	/** Clear all texture loaded in cache. */
	public static void clean() {
		for (Integer texture : textures) {
			glDeleteTextures(texture);
		}
		
		textures.clear();
	}
	
	/**
	 * @param path	The relative path of the image (can be .jpg, .png, .gif, .bmp).
	 * @return		The loaded texture if it has been created succesfully.
	 */
	public static Texture loadTexture(String path) { 								return loadTexture(path, GL_RGBA, GL_NEAREST, false); 	}
	/**
	 * @param path		The relative path of the image (can be .jpg, .png, .gif, .bmp).
	 * @param format	Color format for the texture (default is RGBA).
	 * @return			The loaded texture if it has been created succesfully.
	 */
	public static Texture loadTexture(String path, int format) { 					return loadTexture(path, format, GL_NEAREST, false); 	}
	/**
	 * @param path		The relative path of the image (can be .jpg, .png, .gif, .bmp).
	 * @param filter	Texture magnification filter.
	 * @param mipmap	Use texture mipmapping filter.
	 * @return			The loaded texture if it has been created succesfully.
	 */
	public static Texture loadTexture(String path, int filter, boolean mipmap) { 	return loadTexture(path, GL_RGBA, filter, mipmap); 		}
	/**
	 * @param path		The relative path of the image (can be .jpg, .png, .gif, .bmp).
	 * @param format	Color format for the texture (default is RGBA).
	 * @param filter	Texture magnification filter.
	 * @param mipmap	Use texture mipmapping filter.
	 * @return			The loaded texture if it has been created succesfully.
	 */
	public static Texture loadTexture(String path, int format, int filter, boolean mipmap) {
		if (cache.containsKey(path)) {
			return cache.get(path);
		}
		
		TextureData texture = decode(path);
		
		int id = texture.getID();
		int width = texture.getWidth();
		int height = texture.getHeight();
		
		glBindTexture(GL_TEXTURE_2D, id);
		
		if (mipmap) {
			if (filter == GL_LINEAR) {
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			} else if (filter == GL_NEAREST) {
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST_MIPMAP_NEAREST);
			}
			
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_LOD_BIAS, -1f);
		} else {
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
		}
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		
		glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, texture.getBuffer());
		
		if (mipmap)
			glGenerateMipmap(GL_TEXTURE_2D);
		
		Texture finalTexture = new Texture(id, width, height);
		cache.put(path, finalTexture);
		
		finalTexture.setMipmap(mipmap);
		
		MemoryUtil.memFree(texture.getBuffer());
		
		return finalTexture;
	}
	
	/**
	 * Load a texture with empty data, that will store the content of a framebuffer.
	 * @param dimension		The dimension of the texture.
	 * @param bufferType	The type of FBO: color, depth, stencil or depth & stencil.
	 * @param filter		The texture filter (usually <b>GL_NEAREST</b> or <b>GL_LINEAR</b>).
	 * @return				The loaded texture.
	 */
	public static Texture loadFramebufferTexture(Vector2i dimension, FrameBufferObject.BufferType bufferType, int filter) {
		int id = createTexture();
		
		glBindTexture(GL_TEXTURE_2D, id);
		glTexImage2D(GL_TEXTURE_2D, 0, bufferType.getInternalFormat(), 
				dimension.x, dimension.y, 
				0, bufferType.getFormat(), bufferType.getType(), (ByteBuffer) null);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
		
		glFramebufferTexture2D(GL_FRAMEBUFFER, bufferType.getAttachment(), GL_TEXTURE_2D, id, 0);
		
		Texture finalTexture = new Texture(id, dimension.x, dimension.y);
		return finalTexture;
	}
	
	/**
	 * @param image		The buffer containing the image.
	 * @return			A texture created from the BufferedImage
	 */
	public static Texture getTexture(BufferedImage image) {
		TextureData data = decode(image);
		
		int id = data.getID();
		int width = data.getWidth();
		int height = data.getHeight();
		IntBuffer buffer = data.getBuffer();
		
		glBindTexture(GL_TEXTURE_2D, id);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		glBindTexture(GL_TEXTURE_2D, 0);

		Texture finalTexture = new Texture(id, width, height);

		return finalTexture;
	}
	
	private static TextureData decode(String path) {
		int[] pixels = null;
		int width = 0, height = 0;
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException iae) {
			try {
				image = ImageIO.read(Class.class.getResourceAsStream(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		width = image.getWidth();
		height = image.getHeight();
		pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);

		int[] data = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);

			data[i] = a << 24 | b << 16 | g << 8 | r;
		}

		IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
		buffer.put(data);
		buffer.flip();

		return new TextureData(createTexture(), width, height, buffer);
	}
	
	private static TextureData decode(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[] pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);

		int[] data = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);

			data[i] = a << 24 | b << 16 | g << 8 | r;
		}

		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();

		return new TextureData(createTexture(), width, height, buffer);
	}
	
	/** @return The error texture (used generally in case a texture is missing). */
	public static Texture errorTexture() {
		if (cache.get("error") != null) {
			return cache.get("error");
		}
		int texture = glGenTextures();
		textures.add(texture);
		int[] data = new int[64 * 64];
		for (int x = 0; x < 64; x++) {
			for (int y = 0; y < 64; y++) {
				int i = y * 64 + x;
				data[i] = 0xff000000;
				if (x >= 32 && y < 32)
					data[i] = 0xffff00ff;
				if (x < 32 && y >= 32)
					data[i] = 0xffff00ff;
			}
		}
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();

		glBindTexture(GL_TEXTURE_2D, texture);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 64, 64, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		Texture error = new Texture(texture, 64, 64);
		cache.put("error", error);
		return error;
	}
	
	/** @return The list of previously loaded textures. */
	public static List<Integer> getTextures() { return textures; }
	/** @return The list of textures stored in the cache, as a map. */
	public static Map<String, Texture> getTexturesCache() { return cache; }

}
