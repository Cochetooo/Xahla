#version 330 core

out vec4 fragColor;

in vec3 fragPos;
in vec4 color;
in vec2 normal;
in vec2 texCoord;

void main() {
    fragColor = color * 0.5;
}