package org.xahla.client.platform.domain.repository;

import org.joml.Vector2f;
import org.joml.Vector2i;

/** Window Logic Methods
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public interface VGCWindowCallbackInterface {

    void onClose();

    void onResize(Vector2i newSize);

    void onFrameBufferResize(Vector2i newSize);

    void onContentScale(Vector2f newScale);

    void onMove(Vector2i newPosition);

    void onIconify(boolean iconified);

    void onMaximize(boolean maximized);

    void onFocus(boolean focused);

    void onRefresh();

}
