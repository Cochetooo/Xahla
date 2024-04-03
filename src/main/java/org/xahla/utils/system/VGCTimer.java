package org.xahla.utils.system;

/** Nanoseconds Timer utils for counters
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public final class VGCTimer {

    /*
     * Properties
     */

    private long currentTime;

    /*
     * Constructor
     */

    public VGCTimer() {
        this.reset();
    }

    /*
     * Methods
     */

    public void reset() {
        this.currentTime = System.nanoTime();
    }

    /*
     * Getters - Setters
     */

    public long getElapsedTime() {
        return System.nanoTime() - this.currentTime;
    }

}
