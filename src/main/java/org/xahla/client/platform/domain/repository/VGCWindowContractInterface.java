package org.xahla.client.platform.domain.repository;

import org.joml.Vector2i;
import org.joml.Vector4i;
import org.xahla.client.platform.domain.model.VGCImage;
import org.xahla.client.rendering.domain.enums.VGCRenderingApiEnum;

/** Window Factory contracts interface
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public interface VGCWindowContractInterface {

    void close();

    void setResizable(boolean resizable);

    boolean isResizable();

    void resize(Vector2i newSize);

    void setSizeBounds(Vector2i minimumSize, Vector2i maximumSize);

    Vector2i getSize();

    Vector4i getFrameSize();

    void setPosition(Vector2i newPosition);

    Vector2i getPosition();

    void setTitle(String newTitle);

    void setIcon(VGCImage icon);

    void iconify();

    void setAutoIconify(boolean autoIconify);

    boolean hasAutoIconify();

    void setFloating(boolean floating);

    boolean isFloating();

    void restore();

    void maximize();

    void hide();

    void show();

    void focus();

    void setFocusOnShow(boolean focusOnShow);

    boolean hasFocusOnShow();

    void requestAttention();

    void setOpacity(float opacity);

    float getOpacity();

    void setDecorated(boolean decorated);

    boolean isDecorated();

    boolean supportsRenderer(VGCRenderingApiEnum renderer);

}
