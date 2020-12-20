#version 330 core

in vec2 TexCoords;

out vec4 FragColor;

uniform sampler2D screenTexture;

void main() {
    // Sample code for framebuffer.
	FragColor = vec4(vec3(texture(screenTexture, TexCoords)), 1.0);
}