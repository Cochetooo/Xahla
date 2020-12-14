package org.xahla.main;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector2i;

import xahla.client.graphics.Window;
import xahla.context.ClientContext;
import xahla.core.App;
import xahla.core.IAppCore;

/**
 * This is the main class for your application.
 * 
 * @author {{ author }}
 * @version 1.0
 */
public class {{ programName }} implements IAppCore {

    private static {{ programName }} main;
    private ClientContext context;
    private Window window;

    private Vector2i windowDim;

    public static void main(String[] args) {
        main = new {{ programName }}();

        App.build(
            ClientContext.class,    // The context type
            60,                     // The Update rate
            main                    // The Application Program
        );

        // Start the program
        main.context = ClientContext.instance();
        main.context.request("start");
    }

    @Override
    public void init() {
        this.window = this.context.getWindow();
        this.windowDim = window.getWindowDimension();
    }

    @Override
    public void update() {

    }

    /**
     * Draw a simple rectangle at the center of the window.
     */
    @Override
    public void render() {
        glColor3f(1, 0, 0);
        glBegin(GL_QUADS);
            glVertex2f(windowDim.x / 2 - 100, windowDim.y / 2 - 100);
            glVertex2f(windowDim.x / 2 + 100, windowDim.y / 2 - 100);
            glVertex2f(windowDim.x / 2 + 100, windowDim.y / 2 + 100);
            glVertex2f(windowDim.x / 2 - 100, windowDim.y / 2 + 100);
        glEnd();
    }
    
}
