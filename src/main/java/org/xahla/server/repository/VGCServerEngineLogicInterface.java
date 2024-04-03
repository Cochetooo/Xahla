package org.xahla.server.repository;

/** Server-side Logic methods
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public interface VGCServerEngineLogicInterface {

    default void onClientUpdate()       {}
    default void onServerUpdate()       {}

}
