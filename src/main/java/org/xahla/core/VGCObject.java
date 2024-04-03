package org.xahla.core;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.xahla.core.repository.VGCBaseEngineLogicInterface;

import java.util.ArrayList;
import java.util.List;

/** Object class for engine context
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public abstract class VGCObject implements VGCUuidInterface, VGCBaseEngineLogicInterface, Comparable<VGCObject> {

    /*
     * Data
     */

    @RecordBuilder
    public record Data (
            String name,
            long UUID,
            VGCContext context
    ) {}

    /*
     * Properties
     */

    private final List<VGCComponent> components;
    private final String name;
    private final long UUID;
    private final VGCContext context;

    /*
     * Constructor
     */

    protected VGCObject(Data data) {
        this.name       = data.name();
        this.UUID       = data.UUID();
        this.context    = data.context();
        this.components = new ArrayList<>();
    }

    /*
     * Methods
     */

    /**
     * Adds a component to the object.<br>
     * Safest way to add the component.
     */
    protected void registerComponent(VGCComponent component) {
        if (this.components.contains(component)) {
            return;
        }

        this.components.add(component);
    }

    /*
     * Overrides
     */

    @Override public void onInit() {
        this.components.forEach(VGCComponent::onInit);
    }

    @Override public void onUpdate() {
        this.components.forEach(VGCComponent::onUpdate);
    }

    @Override public void onSecond() {
        this.components.forEach(VGCComponent::onSecond);
    }

    @Override public void onPause() {
        this.components.forEach(VGCComponent::onPause);
    }

    @Override public void onResume() {
        this.components.forEach(VGCComponent::onResume);
    }

    @Override public void onDispose() {
        this.components.forEach(VGCComponent::onDispose);
    }

    @Override public void onExit() {
        this.components.forEach(VGCComponent::onExit);
    }

    @Override public int compareTo(VGCObject o) {
        if (this.UUID - o.UUID < 0) {
            return -1;
        } else if (this.UUID - o.UUID > 0) {
            return 1;
        }

        return 0;
    }

    @Override public long getUUID() {
        return this.UUID;
    }

    @Override public String getName() {
        return this.name;
    }

    /*
     * Getters - Setters
     */

    protected final List<VGCComponent> getComponents() {
        return this.components;
    }
}
