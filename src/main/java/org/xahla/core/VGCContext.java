package org.xahla.core;

import org.xahla.core.repository.VGCBaseEngineLogicInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Context of the app containing objects
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public class VGCContext implements VGCBaseEngineLogicInterface {

    /*
     * Properties
     */

    private List<VGCObject> objects;
    private long objectUUIDCounter;
    private VGCApp app;

    /*
     * Constructor
     */

    public VGCContext(VGCApp app) {
        this.objects = new ArrayList<>();
        this.objectUUIDCounter = 0L;
        this.app = app;
    }

    /*
     * Methods
     */

    public void registerObject(VGCObject object) {
        if (null == object) {
            var exception = new NullPointerException("Null object trying to be registered.");

            this.getApp().getInternalLogger().throwing("VGCContext", "registerObject", exception);
            throw exception;
        }

        this.objects.add(object);
        Collections.sort(this.objects);
    }

    /*
     * Overrides
     */

    @Override public void onInit() {
        this.objects.forEach(VGCObject::onInit);
    }

    @Override public void onUpdate() {
        this.objects.forEach(VGCObject::onUpdate);
    }

    @Override public void onSecond() {
        this.objects.forEach(VGCObject::onSecond);
    }

    @Override public void onPause() {
        this.objects.forEach(VGCObject::onPause);
    }

    @Override public void onResume() {
        this.objects.forEach(VGCObject::onResume);
    }

    @Override public void onDispose() {
        this.objects.forEach(VGCObject::onDispose);
    }

    @Override public void onExit() {
        this.objects.forEach(VGCObject::onExit);
    }

    /*
     * Getters
     */

    /* ##### Next Object UUID ##### */

    public final long getNextUUID() {
        return ++this.objectUUIDCounter;
    }

    /* ##### App ##### */

    public VGCApp getApp() {
        return this.app;
    }

}
