package org.xahla.client.rendering.domain.model;

import org.xahla.client.platform.domain.model.VGCWindow;
import org.xahla.client.rendering.domain.enums.VGCRenderingApiEnum;
import org.xahla.client.rendering.domain.repository.VGCGraphicEngineLogicInterface;
import org.xahla.client.rendering.domain.repository.VGCRendererContractInterface;
import org.xahla.core.helper.VGC_JVMHelper;

/** Abstract renderer class
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public abstract class VGCRenderer implements VGCGraphicEngineLogicInterface, VGCRendererContractInterface {

    /*
     * Properties
     */

    private final VGCWindow window;
    private final VGC_JVMHelper jvmHelper;

    protected final VGCRenderingApiEnum renderingApi;

    /*
     * Constructor
     */

    protected VGCRenderer(
        final VGCWindow pWindow,
        final VGCRenderingApiEnum pRenderingApi
    ) {
        this.window = pWindow;
        this.renderingApi = pRenderingApi;
        this.jvmHelper = new VGC_JVMHelper();
    }

    /*
     * Overrides
     */

    @Override public void onInit() {
        this.validateCompatibility();

        // If program is in debug mode, we call debugger layers.
        if (this.jvmHelper.isDebugMode()) {
            this.setupDebugger();
        }

        this.createContext();
    }

    @Override public void validateCompatibility() {
        if (!window.supportsRenderer(this.renderingApi)) {
            var exception = new IllegalStateException(
                STR."Cannot find a compatible installable client driver for rendering API: \{this.renderingApi.toString()}"
            );

            this.window.getContext().getApp().getInternalLogger().throwing(
                "VGCRenderer",
                "validateCompatibility",
                exception
            );

            throw exception;
        }
    }

    /*
     * Getters
     */

    protected VGCWindow getWindow() {
        return this.window;
    }

}
