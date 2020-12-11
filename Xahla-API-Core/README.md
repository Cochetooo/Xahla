TODO:
Update create:xahla-core
Finish directional lighting
Test 3d
Add other mesh renderers
Add LWJGL quick methods
Clean-up accessors

## 12/12/2020
### 00:26
- Added Global Shader.
- Added Directional Light Object
- Added Rectangle Meshes.
- Added Mouse position getter.
- Added Normals and Colors for meshes.
- Added shortcut accessor for several components.
- Added contains() for XObject to test if it contains a given component.
- Added a function to return list of XObjects that inherits from a class.
- Added Repeat update option for meshes.
- Entity Object can now be attached or detached from the global shader.
- Optimized VAO rendering.
- Projection type are now called "2d" and "3d" instead of "orthographic" and "perspective".
- Fixed: post_init() now call properly.

## 16/09/2020
### 01:30
- **Input** Handling for Keyboard and Mouse
- **Basic Controller**
- Added JavaDoc and sources for Utils module
- Started JavaDoc for Core module

## 15/09/2020
### 19:00
- Reorganized Project: Core and Utils are now split
- Texture, Shaders and VAO
- Orthographic and Perspective Camera and Projection
- Box Collider and Mesh Renderer
- Config are now in JSON format
- Math Functions are now implemented with JOML
- Transform has been remade entirely
- Logger has been improved

## 08/09/2020
### 00:21
- Reorganized packages
- Config
- XObjects and Components
- Transform component
- Renamed "Program" to "Context"

## 30/08/2020
### 01:49
- Fixed other module related issues.

### 01:28
- Fixed inaccessible packages due to module.

### 01:23
- First release of the API
- **App**, **Context** core
- GLFW **Window** Handler
- Client Context side
- Logger, Exception Handler
- Math Utils, Vectors, Matrices, Time Format and Timer
- String and System Utils
- Color class