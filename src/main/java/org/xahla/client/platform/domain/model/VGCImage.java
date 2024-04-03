package org.xahla.client.platform.domain.model;

/** Image Data for pixels array based image
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, February 2024
 */
public record VGCImage(int width, int height, byte[] pixels) {
}
