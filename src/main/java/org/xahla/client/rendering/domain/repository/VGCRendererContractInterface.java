package org.xahla.client.rendering.domain.repository;

/** Interface containing contract methods for renderers.
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public interface VGCRendererContractInterface {

    void validateCompatibility();

    void setupDebugger();

    void createContext();

}
