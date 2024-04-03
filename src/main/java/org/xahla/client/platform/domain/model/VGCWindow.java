package org.xahla.client.platform.domain.model;

import org.xahla.client.VGCClientContext;
import org.xahla.client.platform.domain.repository.VGCWindowContractInterface;
import org.xahla.client.rendering.domain.enums.VGCRenderingApiEnum;
import org.xahla.client.rendering.domain.model.VGCRenderer;
import org.xahla.client.rendering.domain.repository.VGCGraphicEngineLogicInterface;
import org.xahla.client.platform.domain.repository.VGCWindowCallbackInterface;
import org.xahla.client.rendering.infrastructure.opengl.model.VGC_GL_Renderer;
import org.xahla.client.rendering.infrastructure.vulkan.model.VGC_VK_Renderer;

/** Window interface for graphic library framework usage.
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public abstract class VGCWindow implements VGCGraphicEngineLogicInterface, VGCWindowContractInterface {

    /*
     * Properties
     */

    private final VGCWindowCallbackInterface windowPtr;
    private final VGCClientContext context;
    private final VGCRenderer renderer;

    /*
     * Constructor
     */

    protected VGCWindow(
        final VGCWindowCallbackInterface pWindowPtr,
        final VGCClientContext pContext,
        final VGCRenderingApiEnum pRenderingApi
    ) {
        this.context = pContext;
        this.windowPtr = pWindowPtr;

        switch (pRenderingApi) {
            case OPENGL -> {
                this.renderer = new VGC_GL_Renderer(this);
            }
            case VULKAN -> {
                this.renderer = new VGC_VK_Renderer(this, null);
            }
            default -> {
                this.renderer = null;
            }
        }
    }

    /*
     * Methods
     */

    /** Create window **/
    protected abstract void createWindow();

    /** Setup callbacks methods for the extern Window Interface */
    protected abstract void setCallbacks();

    /*
     * Getters - Setters
     */

    /* ##### Window pointer ##### */

    protected final VGCWindowCallbackInterface getWindowPointer() {
        return this.windowPtr;
    }

    public final VGCClientContext getContext() {
        return this.context;
    }

    public final VGCRenderer getRenderer() {
        return this.renderer;
    }

}
