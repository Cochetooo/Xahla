package org.xahla.main;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector2f;

import xahla.client.graphics.Window;
import xahla.context.ClientContext;
import xahla.core.App;
import xahla.core.Context;
import xahla.core.IAppCore;
import xahla.utils.logger.Logger;

/**
 * This is the main class for your application.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public class Fractal implements IAppCore {

    private static Fractal main;
    private ClientContext context;
    private Window window;
    
    private int rendering;

    public static void main(String[] args) {
        main = new Fractal();

        App.build(
            ClientContext.class,    // The context type
            10000,                  // The Framerate
            60,                     // The Update rate
            main                    // The Application Program
        );

        // Start the program
        ClientContext.instance().request("start");
    }

    @Override
    public void init() {
        main.context = ClientContext.instance();
        main.window = main.context.getWindow();
        
        rendering = glGenLists(1);
        
        glNewList(rendering, GL_COMPILE);
        glBegin(GL_LINES);
			//triangle(10, 50, 450, 300, 50, 550, 450, Color4f.GREEN);
			flocon(8, new Vector2f(50, 450), new Vector2f(500, -400));
		glEnd();
		glEndList();
    }
    
    @Override
    public void post_init() {
    	Logger.log("salut lekip est ce que vous mentendez");
    }

    @Override
    public void update() {

    }
    
    @Override
    public void second() {
    	ClientContext.instance().getWindow().changeTitle("Fractale FPS: " + Context.instance().getFPS() + " UPS: " + Context.instance().getUPS());
    }

    /**
     * Draw a simple rectangle at the center of the window.
     */
    @Override
    public void render() {
    	glCallList(rendering);
    }
    
    public void flocon(int n, Vector2f pos, Vector2f dim) {
    	Vector2f tier = new Vector2f(dim).div(3);
    	Vector2f half = new Vector2f(dim).div(2);
    	Vector2f e = new Vector2f(pos).add(half).add(new Vector2f(-tier.y, tier.x));
    	
    	if (n > 0) {
    		flocon(n-1, pos, tier);
    		flocon(n-1, new Vector2f(pos).add(tier), new Vector2f(e).sub(new Vector2f(pos).add(tier)));
    		flocon(n-1, e, new Vector2f(pos).add(new Vector2f(tier).mul(2)).sub(e));
    		flocon(n-1, new Vector2f(pos).add(new Vector2f(tier).mul(2)), tier);
    	} else {
    		line(pos.x, pos.y, pos.x + tier.x, pos.y + tier.y);
        	line(pos.x + tier.x, pos.y + tier.y, e.x, e.y);
        	line(e.x, e.y, pos.x + tier.x * 2, pos.y + tier.y * 2);
        	line(pos.x + tier.x * 2, pos.y + tier.y * 2, pos.x + dim.x, pos.y + dim.y);
    	}
    }
    
    public void triangle(int n, int xa, int ya, int xb, int yb, int xc, int yc) {
    	line(xa, ya, xb, yb);
    	line(xb, yb, xc, yc);
    	line(xc, yc, xa, ya);
    	
    	if (n > 0) {
    		triangle(n-1, xa, ya, (xa+xb)/2, (ya+yb)/2, (xa+xc)/2, (ya+yc)/2);
    		triangle(n-1, xb, yb, (xa+xb)/2, (ya+yb)/2, (xb+xc)/2, (yb+yc)/2);
    		triangle(n-1, xc, yc, (xa+xc)/2, (ya+yc)/2, (xb+xc)/2, (yb+yc)/2);
    	}
    }
    
    public void line(float x0, float y0, float x1, float y1) {
    	glColor3f(0, 1, 0);
    	glVertex2f(x0, y0);
    	glVertex2f(x1, y1);
    }
    
    public Window getWindow() { return window; }
    
	}
