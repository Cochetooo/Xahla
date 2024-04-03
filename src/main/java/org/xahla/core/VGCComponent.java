package org.xahla.core;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.xahla.core.repository.VGCBaseEngineLogicInterface;

/** Components for engine objects
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public abstract class VGCComponent implements VGCBaseEngineLogicInterface {

    /*
     * Data
     */

    @RecordBuilder
    public record Data (
        VGCObject object
    ) {}

    /*
     * Properties
     */

    private final VGCObject object;

    /*
     * Constructor
     */

    protected VGCComponent(Data data) {
        this.object     = data.object();
    }

    /*
     * Getters
     */

    /* ##### Object ##### */
    public VGCObject getParentObject() {
        return this.object;
    }
}
