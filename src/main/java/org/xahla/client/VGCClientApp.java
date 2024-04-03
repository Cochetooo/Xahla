package org.xahla.client;

import org.xahla.client.platform.domain.enums.VGCPlatformApiEnum;
import org.xahla.client.platform.domain.repository.VGCWindowCallbackInterface;
import org.xahla.client.rendering.domain.enums.VGCRenderingApiEnum;
import org.xahla.client.rendering.domain.repository.VGCGraphicEngineLogicInterface;
import org.xahla.core.VGCApp;

import java.util.logging.Logger;

public class VGCClientApp extends VGCApp implements VGCGraphicEngineLogicInterface {

    /*
     * Properties
     */

    private int frameRateLimit, currentFrameRate;
    private double renderTime;
    private double totalElapsedRenderTicks;

    private int renderCounts;

    /*
     * Constructor
     */

    public VGCClientApp(
        final VGCGraphicEngineLogicInterface pUserApp,
        final int pTickRateLimit,
        final int pFrameRateLimit,
        final Logger pLogger
    ) {
        super(
            pUserApp,
            pTickRateLimit,
            pLogger
        );

        this.setFrameRateLimit(pFrameRateLimit);
    }

    /*
     * Methods
     */

    public void createClientContext(
        final VGCRenderingApiEnum pRenderingApiEnum,
        final VGCPlatformApiEnum pPlatformApiEnum,
        final VGCWindowCallbackInterface pWindowPtr
    ) {
        this.context = new VGCClientContext(
            this,
            pRenderingApiEnum,
            pPlatformApiEnum,
            pWindowPtr
        );
    }

    @Override protected void loopEngine() {
        var now = this.getTimer().getElapsedTime();
        var elapsedSecondTime = now - this.getTotalElapsedSecondTicks();
        var elapsedUpdateTime = now - this.getTotalElapsedUpdateTicks();
        var elapsedRenderTime = now - this.totalElapsedRenderTicks;

        if (elapsedSecondTime >= NANOSECONDS) {
            this.currentFrameRate = this.renderCounts;
            this.renderCounts = 0;

            this.handleSeconds(now);
        }

        if (elapsedUpdateTime >= this.getTickTime()) {
            this.handleUpdate(now);
        } else if (elapsedRenderTime >= this.renderTime) {
            this.handleRender(now);
        } else {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void handleRender(long now) {
        this.onRender();

        this.renderCounts++;
        this.totalElapsedRenderTicks += this.renderTime;
    }

    /*
     * Overrides
     */

    @Override public void onRender() {
        this.getContext().onRender();
        ((VGCGraphicEngineLogicInterface) this.getUserApp()).onRender();
    }

    /*
     * Getters - Setters
     */

    /* ##### Frame Rate Limit ##### */

    public final int getFrameRateLimit() {
        return this.frameRateLimit;
    }

    public final void setFrameRateLimit(int pFrameRateLimit) {
        if (pFrameRateLimit < 0) {
            pFrameRateLimit = Integer.MAX_VALUE;
        }

        this.frameRateLimit = pFrameRateLimit;

        this.renderTime = NANOSECONDS / this.frameRateLimit;
    }

    /* ##### Total Elapsed Ticks ##### */

    protected double getTotalElapsedRenderTicks() {
        return this.totalElapsedRenderTicks;
    }

    /* ##### Current Frame Rate ##### */

    public int getCurrentFrameRate() {
        return this.currentFrameRate;
    }

    /* ##### Current Context ##### */

    @Override public VGCClientContext getContext() {
        return (VGCClientContext) super.getContext();
    }

}
