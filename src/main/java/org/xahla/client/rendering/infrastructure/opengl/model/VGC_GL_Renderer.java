package org.xahla.client.rendering.infrastructure.opengl.model;

import org.lwjgl.opengl.GL;
import org.xahla.client.platform.domain.model.VGCWindow;
import org.xahla.client.rendering.domain.enums.VGCRenderingApiEnum;
import org.xahla.client.rendering.domain.model.VGCRenderer;

/** OpenGL Renderer.
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public class VGC_GL_Renderer extends VGCRenderer {

    /*
     * Static field
     */

    private static final VGCRenderingApiEnum RENDERING_API = VGCRenderingApiEnum.OPENGL;

    /*
     * Constructor
     */

    public VGC_GL_Renderer(
        final VGCWindow window
    ) {
        super(window, RENDERING_API);
    }

    /*
     * Overrides
     */

    @Override
    public void setupDebugger() {

    }

    @Override public void createContext() {
        GL.createCapabilities();
    }
}
