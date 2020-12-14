#version 330 core

out vec4 fragColor;

in vec2 FragPos;
in vec4 Color;
in vec2 Normal;

uniform vec4 directionalLight0;
uniform vec4 directionalLight1;
uniform vec4 directionalLight2;
uniform vec4 directionalLight3;
uniform vec4 directionalLight4;
uniform vec4 directionalLight5;
uniform vec4 directionalLight6;
uniform vec4 directionalLight7;
uniform vec4 directionalLight8;
uniform vec4 directionalLight9;

vec4 directionalLights[10] = vec4[] ( 	directionalLight0, directionalLight1, directionalLight2, directionalLight3, directionalLight4,
										directionalLight5, directionalLight6, directionalLight7, directionalLight8, directionalLight9 );

vec3 lightColor = vec3(1.0, 0.75, 0.35);
vec2 norm = normalize(Normal);

vec3 directionalLighting() {
	vec3 result = vec3(0);
	
	for (int i = 0; i < directionalLights.length(); i++) {
		if (directionalLights[i] == vec4(0.0)) break; // No more
		
		vec2 toLight = normalize(directionalLights[i].xy - FragPos);
		float brightness = clamp(dot(toLight, norm), 0.0, 1.0);
		brightness *= clamp(1.0 - (length(toLight) / directionalLights[i].w), 0.0, 1.0);
		result += lightColor * brightness;
	}
	
	return result;
}

void main() {
	float ambientStrength = 0.1;
	vec3 ambient = ambientStrength * lightColor;

	fragColor = Color * vec4(ambient + directionalLighting(), 1.0);
}