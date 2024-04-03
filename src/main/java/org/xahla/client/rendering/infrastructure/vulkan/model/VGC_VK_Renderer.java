package org.xahla.client.rendering.infrastructure.vulkan.model;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkDebugUtilsMessengerCallbackEXT;
import org.lwjgl.vulkan.VkDebugUtilsMessengerCreateInfoEXT;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkLayerProperties;
import org.xahla.client.platform.domain.model.VGCWindow;
import org.xahla.client.rendering.domain.enums.VGCRenderingApiEnum;
import org.xahla.client.rendering.domain.model.VGCRenderer;
import org.xahla.client.rendering.infrastructure.vulkan.helper.VGC_VK_LayersHelper;

import java.nio.IntBuffer;
import java.util.logging.Logger;

import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAllocInt;
import static org.lwjgl.vulkan.EXTDebugUtils.*;
import static org.lwjgl.vulkan.VK10.vkEnumerateInstanceLayerProperties;

/** Vulkan Renderer.
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public class VGC_VK_Renderer extends VGCRenderer {

    /*
     * Static field
     */

    private static final VGCRenderingApiEnum RENDERING_API = VGCRenderingApiEnum.VULKAN;

    /*
     * Properties
     */

    private VkInstance instance;
    private VGC_VK_LayersHelper layersHelper;
    private Logger logger;

    private VkDebugUtilsMessengerCallbackEXT dbgFunc;

    private final IntBuffer ip;

    /*
     * Constructor
     */

    public VGC_VK_Renderer(
        final VGCWindow pWindow,
        final VkDebugUtilsMessengerCallbackEXT pDbgFunc
    ) {
        super(pWindow, RENDERING_API);

        this.logger = pWindow.getContext().getApp().getInternalLogger();
        this.layersHelper = new VGC_VK_LayersHelper(this.logger);
        this.dbgFunc = pDbgFunc;

        this.ip = memAllocInt(1);
    }

    /*
     * Overrides
     */

    @Override public void setupDebugger() {
        try (var stack = MemoryStack.stackPush()) {
            var requiredLayers = this.setupValidationLayers(stack);
            var extensionNames = this.setupExtensions(stack);

            var debuggerInfo = this.enableDebugger(stack);
        }
    }

    @Override public void createContext() {

    }

    /*
     * Methods
     */

    private VkDebugUtilsMessengerCreateInfoEXT enableDebugger(MemoryStack stack) {
        var dbgCreateInfo = VkDebugUtilsMessengerCreateInfoEXT.malloc(stack)
            .sType$Default()
            .pNext(NULL)
            .flags(0)
            .messageSeverity(
                VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT |
                VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT
            )
            .messageType(
                VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT |
                VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT |
                VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT
            );

        if (null != dbgFunc) {
            dbgCreateInfo = dbgCreateInfo.pfnUserCallback(this.dbgFunc);
        }

        dbgCreateInfo = dbgCreateInfo.pUserData(NULL);

        return dbgCreateInfo;
    }

    private PointerBuffer setupValidationLayers(MemoryStack stack) {
        if (!layersHelper.checkValidationLayerSupport(this.ip)) {
            return null;
        }

        PointerBuffer requiredLayers = null;

        // Has Validation Layers
        if (ip.get(0) > 0) {
            requiredLayers = this.layersHelper.getRequiredLayers(stack, ip);
        }

        if (null == requiredLayers) {
            var exception = new IllegalStateException(
                "vkEnumerateInstanceLayerProperties failed to find required validation layer."
            );

            this.logger.throwing(
                this.getClass().getName(),
                "setupDebugger",
                exception
            );

            throw exception;
        }

        return requiredLayers;
    }

    private PointerBuffer setupExtensions(MemoryStack stack) {
        return null;
    }
}
