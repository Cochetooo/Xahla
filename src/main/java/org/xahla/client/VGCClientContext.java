package org.xahla.client;

import org.xahla.client.platform.domain.enums.VGCPlatformApiEnum;
import org.xahla.client.platform.domain.model.VGCWindow;
import org.xahla.client.platform.domain.repository.VGCWindowCallbackInterface;
import org.xahla.client.platform.infrastructure.awt.model.VGC_AWT_Window;
import org.xahla.client.platform.infrastructure.glfw.model.VGC_GLFW_Window;
import org.xahla.client.rendering.domain.enums.VGCRenderingApiEnum;
import org.xahla.client.rendering.domain.repository.VGCGraphicEngineLogicInterface;
import org.xahla.core.VGCContext;

public class VGCClientContext extends VGCContext implements VGCGraphicEngineLogicInterface {

    /*
     * Properties
     */

    private final VGCRenderingApiEnum renderingApiEnum;
    private final VGCPlatformApiEnum platformApiEnum;
    private final VGCWindow window;

    public VGCClientContext(
        VGCClientApp pClientApp,
        VGCRenderingApiEnum pRenderingApiEnum,
        VGCPlatformApiEnum pPlatformApiEnum,
        VGCWindowCallbackInterface pWindowPtr
    ) {
        super(pClientApp);

        this.renderingApiEnum = pRenderingApiEnum;
        this.platformApiEnum = pPlatformApiEnum;

        switch (this.platformApiEnum) {
            case AWT -> {
                this.window = new VGC_AWT_Window(
                    pWindowPtr,
                    this,
                    this.renderingApiEnum
                );
            }
            case GLFW -> {
                this.window = new VGC_GLFW_Window(
                    pWindowPtr,
                    this,
                    this.renderingApiEnum
                );
            }
            default -> {
                this.window = null;
            }
        }
    }

    @Override public void onInit() {
        super.onInit();

        if (null != this.window) {
            this.window.onInit();
        }
    }

    @Override public void onUpdate() {
        super.onUpdate();

        if (null != this.window) {
            this.window.onUpdate();
        }
    }

    @Override public void onRender() {
        if (null != this.window) {
            this.window.onRender();
        }
    }

    /*
     * Getters
     */

    public VGCWindow getWindow() {
        return this.window;
    }

    public VGCPlatformApiEnum getPlatformApi() {
        return this.platformApiEnum;
    }

    public VGCRenderingApiEnum getRenderingApi() {
        return this.renderingApiEnum;
    }
}
