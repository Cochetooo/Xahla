package org.xahla.client.rendering.domain.repository;

import org.xahla.core.repository.VGCBaseEngineLogicInterface;

/** Graphic Logic Methods
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public interface VGCGraphicEngineLogicInterface extends VGCBaseEngineLogicInterface {

    default void onRender()     {}

}
