#version 330 core

layout (location=0) in vec3 in_position;
layout (location=1) in vec4 in_color;
layout (location=2) in vec2 in_normal;
layout (location=3) in vec2 in_texCoord;

out vec4 color;
out vec2 normal;
out vec3 fragPos;
out vec2 texCoord;

uniform mat4 projectionMatrix;

void main() {
    gl_Position = projectionMatrix * vec4(in_position, 1.0);

    fragPos = in_position;
    color = in_color;
    normal = in_normal;
    texCoord = in_texCoord;
}