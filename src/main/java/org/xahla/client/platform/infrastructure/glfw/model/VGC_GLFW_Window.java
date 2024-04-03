package org.xahla.client.platform.infrastructure.glfw.model;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4i;
import org.lwjgl.glfw.*;
import org.lwjgl.system.MemoryStack;
import org.xahla.client.VGCClientContext;
import org.xahla.client.platform.domain.model.VGCImage;
import org.xahla.client.platform.domain.model.VGCWindow;
import org.xahla.client.platform.domain.repository.VGCWindowCallbackInterface;
import org.xahla.client.rendering.domain.enums.VGCRenderingApiEnum;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWVulkan.glfwVulkanSupported;

/** GLFW Window class.
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public class VGC_GLFW_Window extends VGCWindow {

    /*
     * Properties
     */

    private long windowRef;

    /*
     * Constructor
     */

    public VGC_GLFW_Window(
        final VGCWindowCallbackInterface pWindowPtr,
        final VGCClientContext pContext,
        final VGCRenderingApiEnum pRenderingApi
    ) {
        super(pWindowPtr, pContext, pRenderingApi);

        this.windowRef = 0L;
    }

    /*
     * Overrides
     */

    @Override public void onInit() {
        this.createWindow();
        this.setCallbacks();
    }

    /*
     * @TODO factoriser la méthode
     */
    @Override protected void createWindow() {
        var logger = this.getContext().getApp().getInternalLogger();

        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            var exception = new IllegalStateException("Unable to initialize GLFW");

            logger.throwing(
                "VGC_GLFW_Window",
                "createWindow",
                exception
            );

            throw exception;
        }

        glfwDefaultWindowHints();

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        this.windowRef = glfwCreateWindow(300, 300, "Hello World", GLFW_FALSE, GLFW_FALSE);

        if (GLFW_FALSE == this.windowRef) {
            var exception = new IllegalStateException("Failed to create the GLFW Window");

            logger.throwing(
                "VGC_GLFW_Window",
                "createWindow",
                exception
            );

            throw exception;
        }

        var windowDimension = this.getSize();
        var vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        this.setPosition(new Vector2i(
            (vidmode.width() - windowDimension.x) / 2,
            (vidmode.height() - windowDimension.y) / 2
        ));

        glfwMakeContextCurrent(this.windowRef);
        glfwSwapInterval(0);

        this.show();
    }

    @Override protected void setCallbacks() {
        if (null == this.getWindowPointer()) {
            return;
        }

        glfwSetWindowCloseCallback(this.windowRef, new GLFWWindowCloseCallback() {
            @Override
            public void invoke(long window) {
                getWindowPointer().onClose();
            }
        });

        glfwSetWindowSizeCallback(this.windowRef, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                getWindowPointer().onResize(new Vector2i(width, height));
            }
        });

        glfwSetFramebufferSizeCallback(this.windowRef, new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                getWindowPointer().onFrameBufferResize(new Vector2i(width, height));
            }
        });

        glfwSetWindowContentScaleCallback(this.windowRef, new GLFWWindowContentScaleCallback() {
            @Override
            public void invoke(long window, float xScale, float yScale) {
                getWindowPointer().onContentScale(new Vector2f(xScale, yScale));
            }
        });

        glfwSetWindowPosCallback(this.windowRef, new GLFWWindowPosCallback() {
            @Override
            public void invoke(long window, int xPos, int yPos) {
                getWindowPointer().onMove(new Vector2i(xPos, yPos));
            }
        });

        glfwSetWindowIconifyCallback(this.windowRef, new GLFWWindowIconifyCallback() {
            @Override
            public void invoke(long window, boolean iconified) {
                getWindowPointer().onIconify(iconified);
            }
        });

        glfwSetWindowMaximizeCallback(this.windowRef, new GLFWWindowMaximizeCallback() {
            @Override
            public void invoke(long window, boolean maximized) {
                getWindowPointer().onMaximize(maximized);
            }
        });

        glfwSetWindowFocusCallback(this.windowRef, new GLFWWindowFocusCallback() {
            @Override
            public void invoke(long window, boolean focused) {
                getWindowPointer().onFocus(focused);
            }
        });

        glfwSetWindowRefreshCallback(this.windowRef, new GLFWWindowRefreshCallback() {
            @Override
            public void invoke(long window) {
                getWindowPointer().onRefresh();
            }
        });
    }

    @Override public void onUpdate() {
        if (glfwWindowShouldClose(this.windowRef)) {
            this.close();
            this.getContext().getApp().stop();
        }
    }

    @Override public void onRender() {
        glfwSwapBuffers(this.windowRef);
        glfwPollEvents();
    }

    /*
     * Contract Methods
     */

    @Override public void close() {
        glfwSetWindowShouldClose(this.windowRef, true);
    }

    @Override public void setResizable(boolean resizable) {
        glfwSetWindowAttrib(this.windowRef, GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
    }

    @Override public boolean isResizable() {
        return GLFW_TRUE == glfwGetWindowAttrib(this.windowRef, GLFW_RESIZABLE);
    }

    @Override public void resize(Vector2i newSize) {
        glfwSetWindowSize(this.windowRef, newSize.x, newSize.y);
    }

    @Override public void setSizeBounds(Vector2i minimumSize, Vector2i maximumSize) {
        glfwSetWindowSizeLimits(
            this.windowRef,
            minimumSize.x, minimumSize.y,
            maximumSize.x, maximumSize.y
        );
    }

    @Override public Vector2i getSize() {
        var size = new Vector2i();

        try (var stack = MemoryStack.stackPush()) {
            var width = stack.mallocInt(1);
            var height = stack.mallocInt(1);

            glfwGetWindowSize(this.windowRef, width, height);

            size.set(width.get(0), height.get(0));
        }

        return size;
    }

    @Override public Vector4i getFrameSize() {
        var size = new Vector4i();

        try (var stack = MemoryStack.stackPush()) {
            var left = stack.mallocInt(1);
            var top = stack.mallocInt(1);
            var right = stack.mallocInt(1);
            var bottom = stack.mallocInt(1);

            glfwGetWindowFrameSize(this.windowRef, left, top, right, bottom);

            size.set(left.get(0), top.get(0), right.get(0), bottom.get(0));
        }

        return size;
    }

    @Override public void setPosition(Vector2i newPosition) {
        glfwSetWindowPos(
            this.windowRef,
            newPosition.x, newPosition.y
        );
    }

    @Override public Vector2i getPosition() {
        var size = new Vector2i();

        try (var stack = MemoryStack.stackPush()) {
            var x = stack.mallocInt(1);
            var y = stack.mallocInt(1);

            glfwGetWindowPos(this.windowRef, x, y);

            size.set(x.get(0), y.get(0));
        }

        return size;
    }

    @Override public void setTitle(String newTitle) {
        glfwSetWindowTitle(
            this.windowRef,
            newTitle
        );
    }

    @Override public void setIcon(VGCImage icon) {
        if (null == icon) {
            glfwSetWindowIcon(this.windowRef, null);
            return;
        }

        try (var stack = MemoryStack.stackPush()) {
            var pixels = stack.bytes(icon.pixels());

            try (var glfwBuffer = GLFWImage.create(1)) {
                try (var iconGI = GLFWImage.create().set(icon.width(), icon.height(), pixels)) {
                    glfwBuffer.put(0, iconGI);

                    glfwSetWindowIcon(this.windowRef, glfwBuffer);
                }
            }
        }
    }

    @Override public void iconify() {
        glfwIconifyWindow(this.windowRef);
    }

    @Override public void setAutoIconify(boolean autoIconify) {
        glfwSetWindowAttrib(this.windowRef, GLFW_AUTO_ICONIFY, autoIconify ? GLFW_TRUE : GLFW_FALSE);
    }

    @Override public boolean hasAutoIconify() {
        return GLFW_TRUE == glfwGetWindowAttrib(this.windowRef, GLFW_AUTO_ICONIFY);
    }

    @Override public void setFloating(boolean floating) {
        glfwSetWindowAttrib(this.windowRef, GLFW_FLOATING, floating ? GLFW_TRUE : GLFW_FALSE);
    }

    @Override public boolean isFloating() {
        return GLFW_TRUE == glfwGetWindowAttrib(this.windowRef, GLFW_FLOATING);
    }

    @Override public void restore() {
        glfwRestoreWindow(this.windowRef);
    }

    @Override public void maximize() {
        glfwMaximizeWindow(this.windowRef);
    }

    @Override public void hide() {
        glfwHideWindow(this.windowRef);
    }

    @Override public void show() {
        glfwShowWindow(this.windowRef);
    }

    @Override public void focus() {
        glfwFocusWindow(this.windowRef);
    }

    @Override public void setFocusOnShow(boolean focusOnShow) {
        glfwSetWindowAttrib(this.windowRef, GLFW_FOCUS_ON_SHOW, focusOnShow ? GLFW_TRUE : GLFW_FALSE);
    }

    @Override public boolean hasFocusOnShow() {
        return GLFW_TRUE == glfwGetWindowAttrib(this.windowRef, GLFW_FOCUS_ON_SHOW);
    }

    @Override public void requestAttention() {
        glfwRequestWindowAttention(this.windowRef);
    }

    @Override public void setOpacity(float opacity) {
        if (GLFW_FALSE == glfwGetWindowAttrib(this.windowRef, GLFW_TRANSPARENT_FRAMEBUFFER)) {
            return;
        }

        glfwSetWindowOpacity(this.windowRef, opacity);
    }

    @Override public float getOpacity() {
        return glfwGetWindowOpacity(this.windowRef);
    }

    @Override public void setDecorated(boolean decorated) {
        glfwSetWindowAttrib(this.windowRef, GLFW_DECORATED, decorated ? GLFW_TRUE : GLFW_FALSE);
    }

    @Override public boolean isDecorated() {
        return GLFW_TRUE == glfwGetWindowAttrib(this.windowRef, GLFW_DECORATED);
    }

    @Override public boolean supportsRenderer(VGCRenderingApiEnum renderer) {
        switch (renderer) {
            case OPENGL, NONE -> {
                return true;
            }
            case VULKAN -> {
                return glfwVulkanSupported();
            }
        }

        return false;
    }

    /*
     * Additional methods
     */

    public void setAspectRatio(Vector2i ratio) {
        glfwSetWindowAspectRatio(
            this.windowRef,
            ratio.x, ratio.y
        );
    }

    public Vector2i getFrameBufferSize() {
        var size = new Vector2i();

        try (var stack = MemoryStack.stackPush()) {
            var width = stack.mallocInt(1);
            var height = stack.mallocInt(1);

            glfwGetFramebufferSize(this.windowRef, width, height);

            size.set(width.get(0), height.get(0));
        }

        return size;
    }

    public Vector2f getContentScale() {
        var size = new Vector2f();

        try (var stack = MemoryStack.stackPush()) {
            var xScale = stack.mallocFloat(1);
            var yScale = stack.mallocFloat(1);

            glfwGetWindowContentScale(this.windowRef, xScale, yScale);

            size.set(xScale.get(0), yScale.get(0));
        }

        return size;
    }

}
