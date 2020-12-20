## 19/12/2020
- Added: Framebuffered Client Context, which contains a whole scene Framebuffer with an integrated shader.
- Added: Framebuffer and Renderbuffer
- Added: post_render() for post rendering.
- Changed: Moved VBO, RBO and FBO into graphics.objects package.
- Improved: Cleant up Core engine.
- Fixed: pre_render() and post_update() now call properly.

## 13/12/2020
- Added an option to change the cursor mode.
- Viewport now correctly updates when the window is resized.
- Fixed: Shaders and textures files path now works when exporting to JAR.
- Fixed: post_init() now call properly for the app instance.
- Fixed: resize() now call properly.

## 12/12/2020
### 22:00
- Added MSAA.
- Added option for window decoration, fullscreen and color buffer bits.
- Added an option to center cursor on startup.
- Added an option to set the window as floating over all other applications.
- Framerate is now set on the rendering config file.

### 00:30
- Added Global Shader.
- Added Directional Light Object.
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
- Fixed: post_init() now call properly for context.

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