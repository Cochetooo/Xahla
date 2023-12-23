package core;

/** App instance
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, December 2023
 */
public class App implements BaseEngineLogicInterface {

    /*
     * Properties
     */

    private BaseEngineLogicInterface app;

    private boolean paused;

    private int ups, fps;
    private double tickTime, renderTime;

    private boolean running;

    /*
     * Constructor
     */

    public App(BaseEngineLogicInterface pApp) {
        this.app = pApp;
        this.paused = false;
        this.ups = 0;
        this.fps = 0;
        this.tickTime = 0.0;
        this.renderTime = 0.0;
        this.running = false;
    }

    /*
     * Getters - Setters
     */

    public boolean isPaused() {
        return this.paused;
    }

    public void setPaused(boolean paused) {
        if (!paused) {
            this.onResume();
        }

        this.paused = paused;
    }

    public int getUpdatePerSecond() {
        return this.ups;
    }

    public int getFramerate() {
        return this.fps;
    }

}
