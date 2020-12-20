#version 330 core

layout (location=0) in vec2 in_position;
layout (location=1) in vec4 in_color;
layout (location=2) in vec2 in_normal;

out vec4 Color;
out vec2 Normal;
out vec2 FragPos;

uniform mat4 projectionMatrix;

void main() {
    gl_Position = projectionMatrix * vec4(in_position, 0.0, 1.0);
    FragPos = in_position;
    Color = in_color;
    Normal = in_normal;
}