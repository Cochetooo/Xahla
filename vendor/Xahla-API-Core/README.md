## a0.2.4 (20/12/2020)
- Added: **Screenshot**
- Added: Mouse wheel and touchpad scroll getter
- Added: Framebuffer Texture Loading
- Improved: Memory Stack
- Improved: Texture

## a0.2.3 (19/12/2020)
- Added: Framebuffered Client Context, which contains a whole scene Framebuffer with an integrated shader
- Added: **Framebuffer** and **Renderbuffer**
- Added: post_render() for post rendering
- Changed: Moved VBO, RBO and FBO into graphics.objects package
- Improved: Cleant up Core engine
- Fixed: pre_render() and post_update() now call properly

## a0.2.2 (13/12/2020)
- Added: Option to change the cursor mode
- Fixed: Viewport now correctly updates when the window is resized
- Fixed: Shaders and textures files path now works when exporting to JAR
- Fixed: post_init() now call properly for the app instance
- Fixed: resize() now call properly

## a0.2.1 (12/12/2020)
- Added: **MSAA**.
- Added: option for window decoration, fullscreen and color buffer bits
- Added: an option to center cursor on startup
- Added: an option to set the window as floating over all other applications
- Changed: Framerate is now set on the rendering config file

## a0.2.0 (11/12/2020)
- Added: **Global Shader**
- Added: **Directional Light** Object
- Added: **Rectangle Meshes**
- Added: Mouse position getter
- Added: Normals and Colors for meshes
- Added: JavaDoc and sources for Core module
- Added: shortcut accessor for several components
- Added: contains() for XObject to test if it contains a given component
- Added: a function to return list of XObjects that inherits from a class
- Added: Repeat update option for meshes
- Changed: Entity Object can now be attached or detached from the global shader
- Changed: Projection type are now called "2d" and "3d" instead of "orthographic" and "perspective"
- Improved: VAO rendering
- Fixed: post_init() now call properly for context

## a0.1.3 (16/09/2020)
- Added: **Input** Handling for Keyboard and Mouse
- Added: **Basic Controller**
- Added: JavaDoc and sources for Utils module

## a0.1.2 (15/09/2020)
- Added: **Texture**, **Shaders** and **VAO**
- Added: **Orthographic & Perspective Camera**, **Projection**
- Added: **Box Collider** and **Mesh Renderer**
- Changed: Reorganized Project, Core and Utils are now split
- Changed: Config are now in JSON format
- Changed: Math Functions are now implemented with JOML
- Changed: Transform has been remade entirely
- Improved: Logger

## a0.1.1 (08/09/2020)
- Added: **Configs**
- Added: **XObjects** and **Components**
- Added: **Transform** component
- Changed: Reorganized packages
- Changed: Renamed "Program" to "Context"

## a0.1.0 (30/08/2020)
- Added: **App**, **Context** core
- Added: GLFW **Window** Handler
- Added: **Client Context** side
- Added: **Logger**, **Exception Handler**
- Added: **Math Utils**, **Vectors**, **Matrices**, **Time Format** and **Timer**
- Added: **String & System Utils**
- Added: **Color** class
- Fixed: Other modules related issues
- Fixed: Inaccessible packages due to module