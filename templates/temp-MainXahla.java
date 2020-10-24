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

    public static void main(String[] args) {
        main = new {{ programName }}();

        App.build(
            ClientContext.class,    // The context type
            60,                     // The Framerate
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
    }

    @Override
    public void update() {

    }

    /**
     * Draw a simple rectangle at the center of the window.
     */
    @Override
    public void render() {
        Vector2i dim = new Vector2i(
            window.getWindowDimension().x / 2,
            window.getWindowDimension().y / 2
        );

        glColor3f(1, 0, 0);
        glBegin(GL_QUADS);
            glVertex2f(dim.x - 100, dim.y - 100);
            glVertex2f(dim.x + 100, dim.y - 100);
            glVertex2f(dim.x + 100, dim.y + 100);
            glVertex2f(dim.x - 100, dim.y + 100);
        glEnd();
    }
    
}
