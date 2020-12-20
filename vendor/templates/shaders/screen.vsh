#version 330 core

layout (location = 0) in vec2 in_position;
layout (location = 1) in vec2 in_texCoord;

out vec2 TexCoords;

void main() {
    gl_Position = vec4(in_position.xy, 0.0, 1.0);
    TexCoords = in_texCoord;
}