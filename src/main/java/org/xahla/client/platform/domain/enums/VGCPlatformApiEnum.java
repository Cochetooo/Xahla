package org.xahla.client.platform.domain.enums;

/** Enum of all platform API for creating windows, contexts and surfaces.
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public enum VGCPlatformApiEnum {

    /**
     * Specifies that the app context will use Java AWT (Abstract Window Toolkit),
     * a platform-independent GUI (Graphical User Interface) toolkit.<br><br>
     *
     * Support following rendering methods:<br>
     * <b>Pane</b>
     */
    AWT,


    /**
     * Specifies that the app context will use GLFW (Graphics Library Framework), a lightweight,
     * cross-platform library for creating and managing windows, contexts
     * (like OpenGL or Vulkan rendering contexts), and handling input events such as keyboard,
     * mouse, and joystick events in a simple and consistent manner.
     * It is primarily used in applications that require low-level
     * access to rendering and input handling functionality.<br><br>
     *
     * Supports following rendering methods:<br>
     * <b>OpenGL</b>, <b>Vulkan</b>
     */
    GLFW,

    /**
     * Specifies that the app context will not use any Graphic Framework, and that you
     * can fully customize the way your client context will perform.
     */
    CUSTOM

}
