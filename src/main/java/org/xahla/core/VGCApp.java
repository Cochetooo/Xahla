package org.xahla.core;

import org.xahla.core.repository.VGCBaseEngineLogicInterface;
import org.xahla.utils.system.VGCTimer;

import java.util.logging.Logger;

/** Main App running program
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public class VGCApp implements VGCBaseEngineLogicInterface {

    /*
     * Static fields
     */

    protected static final double NANOSECONDS = 1_000_000_000.0;

    /*
     * Properties
     */

    private final VGCBaseEngineLogicInterface userApp;

    private int tickRateLimit, currentTickRate;
    private int seconds;
    private double tickTime;
    private double totalElapsedUpdateTicks, totalElapsedSecondTicks;

    private int updateCounts;

    private boolean running, paused;

    private VGCTimer timer;

    protected VGCContext context;

    public Logger internalLogger;

    /*
     * Constructor
     */

    public VGCApp(
        final VGCBaseEngineLogicInterface pUserApp,
        final int pTickRateLimit,
        final Logger pLogger
    ) {
        this.userApp = pUserApp;
        this.setTickRateLimit(pTickRateLimit);

        this.internalLogger = pLogger;
        // @TODO Set Global Exception Handler

        this.running = false;
    }

    /*
     * Methods
     */

    public void createContext() {
        this.context = new VGCContext(this);
    }

    public final void run() {
        if (this.running) {
            return;
        }

        this.onInit();

        this.running = true;
        this.timer = new VGCTimer();

        while (this.running) {
            if (this.paused) {
                this.onPause();
                continue;
            }

            this.loopEngine();
        }

        this.onExit();
        System.exit(0);
    }

    public final void stop() {
        this.running = false;
    }

    protected void loopEngine() {
        var now = this.timer.getElapsedTime();
        var elapsedSecondTime = now - this.totalElapsedSecondTicks;
        var elapsedUpdateTime = now - this.totalElapsedUpdateTicks;

        if (elapsedSecondTime >= NANOSECONDS) {
            this.handleSeconds(now);
        }

        if (elapsedUpdateTime >= this.tickTime) {
            this.handleUpdate(now);
        } else {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void handleSeconds(long now) {
        this.seconds++;
        this.currentTickRate = this.updateCounts;
        this.updateCounts = 0;

        this.onSecond();

        this.totalElapsedSecondTicks = now;
    }

    protected void handleUpdate(long now) {
        this.onUpdate();

        this.updateCounts++;
        this.totalElapsedUpdateTicks += this.tickTime;
    }

    /*
     * Overrides
     */

    @Override public void onInit() {
        this.context.onInit();
        this.userApp.onInit();
    }

    @Override public void onUpdate() {
        this.context.onUpdate();
        this.userApp.onUpdate();
    }

    @Override public void onSecond() {
        this.context.onSecond();
        this.userApp.onSecond();
    }

    @Override public void onPause() {
        this.context.onPause();
        this.userApp.onPause();
    }

    @Override public void onResume() {
        this.context.onResume();
        this.userApp.onResume();
    }

    @Override public void onDispose() {
        this.context.onDispose();
        this.userApp.onDispose();
    }

    @Override public void onExit() {
        this.context.onExit();
        this.userApp.onExit();
    }

    /*
     * Getters - Setters
     */

    /* ##### App ##### */

    protected VGCBaseEngineLogicInterface getUserApp() {
        return this.userApp;
    }

    /* ##### Tick Rate ##### */

    public final int getTickRateLimit() {
        return this.tickRateLimit;
    }

    public final void setTickRateLimit(int pTickRateLimit) {
        if (pTickRateLimit < 0) {
            pTickRateLimit = Integer.MAX_VALUE;
        }

        this.tickRateLimit = pTickRateLimit;

        this.tickTime = NANOSECONDS / this.tickRateLimit;
    }

    /* ##### Total Elapsed Ticks ##### */

    protected final double getTotalElapsedSecondTicks() {
        return this.totalElapsedSecondTicks;
    }

    protected final double getTotalElapsedUpdateTicks() {
        return this.totalElapsedUpdateTicks;
    }

    /* ##### Current Tick Rate ##### */

    public final int getCurrentTickRate() {
        return this.currentTickRate;
    }

    /* ##### Second Time ##### */

    public final int getSeconds() {
        return this.seconds;
    }

    /* ##### Tick Time ##### */

    protected final double getTickTime() {
        return this.tickTime;
    }

    /* ##### Running ##### */

    public final boolean isRunning() {
        return this.running;
    }

    /* ##### Paused ##### */

    public final boolean isPaused() {
        return this.paused;
    }

    public final void setPaused(boolean pPaused) {
        this.paused = pPaused;

        if (!paused) {
            this.onResume();
        }
    }

    /* ##### Timer ##### */

    protected final VGCTimer getTimer() {
        return this.timer;
    }

    /* ##### Context ##### */

    public VGCContext getContext() {
        return this.context;
    }

    /* ##### Logger ##### */

    public Logger getInternalLogger() {
        return this.internalLogger;
    }

}
