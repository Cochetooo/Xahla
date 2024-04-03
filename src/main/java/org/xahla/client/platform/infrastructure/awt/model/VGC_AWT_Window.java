package org.xahla.client.platform.infrastructure.awt.model;

import org.joml.Vector2i;
import org.joml.Vector4i;
import org.xahla.client.VGCClientContext;
import org.xahla.client.platform.domain.model.VGCImage;
import org.xahla.client.platform.domain.model.VGCWindow;
import org.xahla.client.platform.domain.repository.VGCWindowCallbackInterface;
import org.xahla.client.rendering.domain.enums.VGCRenderingApiEnum;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/** AWT Window class.
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public class VGC_AWT_Window extends VGCWindow {

    /*
     * Properties
     */

    private final Frame windowRef;
    private boolean focusOnShow;

    /*
     * Constructor
     */

    public VGC_AWT_Window(
        final VGCWindowCallbackInterface pWindowPtr,
        final VGCClientContext pContext,
        final VGCRenderingApiEnum pRenderingApi
    ) {
        super(pWindowPtr, pContext, pRenderingApi);

        this.windowRef = new Frame();
    }

    /*
     * Overrides
     */

    @Override public void onInit() {
        this.createWindow();
        this.setCallbacks();
    }

    @Override protected void createWindow() {
        this.resize(new Vector2i(300, 300));
        this.setTitle("Window");
        this.windowRef.setLayout(null);
        this.show();
    }

    @Override protected void setCallbacks() {
        this.windowRef.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (null != getWindowPointer()) {
                    getWindowPointer().onFocus(true);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (null != getWindowPointer()) {
                    getWindowPointer().onFocus(false);
                }
            }
        });

        this.windowRef.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                if (null != getWindowPointer()) {
                    getWindowPointer().onClose();
                }

                close();
                getContext().getApp().stop();
            }

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {
                if (null != getWindowPointer()) {
                    getWindowPointer().onIconify(true);
                }
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                if (null != getWindowPointer()) {
                    getWindowPointer().onIconify(false);
                }
            }

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
    }

    /*
     * Contract Methods
     */

    @Override public void close() {
        this.windowRef.dispose();
    }

    @Override public void setResizable(boolean resizable) {
        this.windowRef.setResizable(resizable);
    }

    @Override
    public boolean isResizable() {
        return this.windowRef.isResizable();
    }

    @Override public void resize(Vector2i newSize) {
        this.windowRef.setSize(newSize.x, newSize.y);
    }

    @Override public void setSizeBounds(Vector2i minimumSize, Vector2i maximumSize) {
        this.windowRef.setMinimumSize(new Dimension(minimumSize.x, minimumSize.y));
        this.windowRef.setMaximumSize(new Dimension(maximumSize.x, maximumSize.y));
    }

    @Override public Vector2i getSize() {
        var dim = this.windowRef.getSize();

        return new Vector2i(dim.width, dim.height);
    }

    @Override public Vector4i getFrameSize() {
        this.getContext().getApp().getInternalLogger().warning(
            "[VGC_AWT_WindowContracts::getFrameSize] Frame Size is not supported by AWT, returning Vector zero."
        );

        return new Vector4i();
    }

    @Override public void setPosition(Vector2i newPosition) {
        this.windowRef.setLocation(newPosition.x, newPosition.y);
    }

    @Override public Vector2i getPosition() {
        var location = this.windowRef.getLocationOnScreen();

        return new Vector2i(location.x, location.y);
    }

    @Override public void setTitle(String newTitle) {
        this.windowRef.setTitle(newTitle);
    }

    @Override public void setIcon(VGCImage icon) {
        try {
            var image = ImageIO.read(new ByteArrayInputStream(icon.pixels()));

            this.windowRef.setIconImage(image);
        } catch (IOException e) {
            this.getContext().getApp().getInternalLogger().throwing(
                "VGC_AWT_WindowContracts",
                "setIcon",
                e
            );

            throw new RuntimeException(e);
        }
    }

    @Override public void iconify() {
        this.windowRef.setExtendedState(Frame.ICONIFIED);
    }

    @Override public void setAutoIconify(boolean autoIconify) {
        this.getContext().getApp().getInternalLogger().warning(
            "[VGC_AWT_WindowContracts::setAutoIconify] Auto Iconify is not supported by AWT."
        );
    }

    @Override
    public boolean hasAutoIconify() {
        this.getContext().getApp().getInternalLogger().warning(
            "[VGC_AWT_WindowContracts::hasAutoIconify] Auto Iconify is not supported by AWT."
        );

        return false;
    }

    @Override
    public void setFloating(boolean floating) {
        this.getContext().getApp().getInternalLogger().warning(
            "[VGC_AWT_WindowContracts::setFloating] Floating window is not supported by AWT."
        );
    }

    @Override
    public boolean isFloating() {
        this.getContext().getApp().getInternalLogger().warning(
            "[VGC_AWT_WindowContracts::isFloating] Floating window is not supported by AWT."
        );

        return false;
    }

    @Override public void restore() {
        this.windowRef.setExtendedState(Frame.NORMAL);
    }

    @Override public void maximize() {
        this.windowRef.setExtendedState(Frame.MAXIMIZED_BOTH);
    }

    @Override public void hide() {
        this.windowRef.setVisible(false);
    }

    @Override public void show() {
        this.windowRef.setVisible(true);

        if (this.focusOnShow) {
            this.focus();
        }
    }

    @Override public void focus() {
        this.windowRef.requestFocus();
    }

    @Override
    public void setFocusOnShow(boolean pFocusOnShow) {
        this.focusOnShow = pFocusOnShow;
    }

    @Override
    public boolean hasFocusOnShow() {
        return this.focusOnShow;
    }

    @Override public void requestAttention() {
        this.getContext().getApp().getInternalLogger().warning(
            "[VGC_AWT_WindowContracts::requestAttention] Attention request in taskbar is not supported by AWT."
        );
    }

    @Override public void setOpacity(float opacity) {
        if (this.isDecorated()) {
            return;
        }

        this.windowRef.setOpacity(opacity);
    }

    @Override public float getOpacity() {
        return this.windowRef.getOpacity();
    }

    @Override public void setDecorated(boolean decorated) {
        this.windowRef.setUndecorated(!decorated);
    }

    @Override public boolean isDecorated() {
        return !this.windowRef.isUndecorated();
    }

    @Override
    public boolean supportsRenderer(VGCRenderingApiEnum renderer) {
        return false;
    }
}
