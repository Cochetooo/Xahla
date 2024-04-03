package org.xahla.client.rendering.infrastructure.vulkan.helper;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkLayerProperties;

import java.nio.IntBuffer;
import java.util.logging.Logger;

import static org.lwjgl.vulkan.VK10.vkEnumerateInstanceLayerProperties;

/** Helper class for Vulkan validation layers
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public class VGC_VK_LayersHelper {

    /*
     * Properties
     */

    private Logger logger;

    /*
     * Constructor
     */

    public VGC_VK_LayersHelper(
        final Logger pLogger
    ) {
        this.logger = pLogger;
    }

    /*
     * Methods
     */

    public PointerBuffer checkLayers(MemoryStack stack, VkLayerProperties.Buffer available, String... layers) {
        var required = stack.mallocPointer(layers.length);

        for (int i = 0; i < layers.length; i++) {
            var found = false;

            for (int j = 0; j < available.capacity(); j++) {
                available.position(j);

                if (layers[i].equals(available.layerNameString())) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                this.logger.warning(
                    STR."Cannot find layer: \{layers[i]}\n"
                );

                return null;
            }

            required.put(i, stack.ASCII(layers[i]));
        }

        return required;
    }

    public boolean checkValidationLayerSupport(IntBuffer ip) {
        var instanceLayer = vkEnumerateInstanceLayerProperties(ip, null);

        if (0 != instanceLayer) {
            this.logger.warning(
                "Devices does not support Vulkan validation layers."
            );

            return false;
        }

        return true;
    }

    public PointerBuffer getRequiredLayers(MemoryStack stack, IntBuffer ip) {
        var availableLayers = VkLayerProperties.malloc(ip.get(0), stack);
        check(vkEnumerateInstanceLayerProperties(ip, availableLayers));

        PointerBuffer requiredLayers;

        requiredLayers = this.checkLayers(
            stack,
            availableLayers,
            "VK_LAYER_KHRONOS_validation"
            /* @TODO compatibility "VK_LAYER_LUNARG_assistant_layer" */
        );

        if (null == requiredLayers) {
            requiredLayers = this.checkLayers(
                stack,
                availableLayers,
                "VK_LAYER_LUNARG_standard_validation"
            );

            if (null != requiredLayers) {
                this.logger.warning(
                    "Couldn't find default Vulkan validation layers, using alternative (deprecated): " +
                        "VK_LAYER_LUNARG_standard_validation"
                );
            }
        }

        if (null == requiredLayers) {
            requiredLayers = this.checkLayers(
                stack,
                availableLayers,
                "VK_LAYER_GOOGLE_threading",
                "VK_LAYER_LUNARG_parameter_validation",
                "VK_LAYER_LUNARG_object_tracker",
                "VK_LAYER_LUNARG_core_validation",
                "VK_LAYER_GOOGLE_unique_objects"
            );

            if (null != requiredLayers) {
                this.logger.warning(
                    "Couldn't find default Vulkan validation layers, using alternative (deprecated): " +
                        "VK_LAYER_GOOGLE_threading, VK_LAYER_LUNARG_parameter_validation, " +
                        "VK_LAYER_LUNARG_object_racker, VK_LAYER_LUNARG_core_validation, " +
                        "VK_LAYER_GOOGLE_unique_objects"
                );
            }
        }

        return requiredLayers;
    }

    private void check(int errCode) {
        if (0 != errCode) {
            var exception = new IllegalStateException(STR."Vulkan error [0x\{errCode}]");

            this.logger.throwing(
                this.getClass().getName(),
                "check",
                exception
            );

            throw exception;
        }
    }

}
