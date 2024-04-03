package org.xahla.client.rendering.domain.enums;

public enum VGCRenderingApiEnum {

    /**
     * Specifies the context that the rendering method will be OpenGL,
     * a cross-platform, open-source API for rendering 2D and 3D vector graphics.
     * It provides a set of functions and commands that developers
     * can use to interact with graphics hardware and produce
     * high-quality graphical output in real-time applications.
     */
    OPENGL,

    /**
     * Specifies the context that the rendering method will be Vulkan,
     * a low-overhead, cross-platform graphics and compute API.
     * It provides developers with explicit control over graphics hardware,
     * allowing for high-performance rendering and parallel computation
     * on modern GPUs.
     */
    VULKAN,

    /**
     * Specifies the context that no rendering method will be used.
     * The user will have to implement his own way of rendering
     * within the window.
     */
    NONE

}
