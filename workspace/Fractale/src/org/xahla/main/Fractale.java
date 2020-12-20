package org.xahla.main;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector2i;

import xahla.client.graphics.Window;
import xahla.context.FBClientContext;
import xahla.core.App;
import xahla.core.IAppCore;
import xahla.utils.logger.Level;
import xahla.utils.logger.Logger;

/**
 * This is the main class for your application.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public class Fractale implements IAppCore {

    private static Fractale main;
    private FBClientContext context;
    private Window window;
    
    private int listIndex;

    private Vector2i windowDim;

    public static void main(String[] args) {
        main = new Fractale();

        Logger.debugShow = Level.FINE;

        App.build(
            FBClientContext.class,  // The context type
            60,                     // The Update rate
            main                    // The Application Program
        );

        // Start the program
        main.context = FBClientContext.instance();
        main.context.request("start");
    }

    @Override
    public void init() {
        this.window = this.context.getWindow();
        this.windowDim = window.getWindowDimension();
        
        listIndex = glGenLists(1);
        
        glNewList(listIndex, GL_COMPILE);
	        glColor3f(1, 0, 0);
	        glBegin(GL_LINES);
	            glVertex2f(100, 300);
	            glVertex2f(300, 300);
	        glEnd();
        glEndList();
    }

    @Override
    public void update() {
    	
    }

    /**
     * Draw a simple rectangle at the center of the window.
     */
    @Override
    public void render() {
        glCallList(listIndex);
    }
    
    @Override
    public void dispose() {
    	glDeleteLists(listIndex, 1);
    }
}
