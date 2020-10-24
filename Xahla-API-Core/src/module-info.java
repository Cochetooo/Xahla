/**
 * The module for the Xahla API Core.
 * 
 * @author Cochetooo
 * @version 1.1
 */
module xahla.api.core {
	requires transitive java.desktop;
	
	requires transitive org.lwjgl.glfw;
	requires transitive org.lwjgl.opengl;
	requires transitive org.joml;
	
	requires transitive xahla.api.utils;
	requires transitive org.json;
	requires java.logging;
	requires java.net.http;
	
	exports xahla.client.graphics;
	exports xahla.client.input;
	
	exports xahla.context;
	exports xahla.context.components;
	exports xahla.context.objects;
	
	exports xahla.core;
}