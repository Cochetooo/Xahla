package org.xahla.core.repository;

/** Base logic methods for engine objects
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public interface VGCBaseEngineLogicInterface {

    default void onInit()       {}

    default void onUpdate()     {}
    default void onSecond()     {}

    default void onPause()      {}
    default void onResume()     {}

    default void onDispose()    {}
    default void onExit()       {}

}
