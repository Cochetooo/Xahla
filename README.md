# Xahla
The perfect tool for Java/Kotlin video game developers.

Xahla is a framework that greatly ease the development of video games in Java by having its own automated workspace.
With our product, you'll be able to design:

→ OpenGL based projects in Java with an Entity-Component System (Vulkan support is to be coming)

→ Software based for GUI projects in Java and XML with a Model View View-model Architecture

→ Server Side based projects in Java with a Plugin Handler with an Event-Driven Architecture

The workspace includes a manager for JSON API relations by HTTP Request (2.0).
It also includes XMake, a command-line executable that add a bundle of utility commands such as project creation, or auto-generated class.

# Install
To use our workspace, you will need Java Runtime Environment 16 or higher on your computer.
/!\ The framework isn't ready to function yet. /!\ 

All your projects are stored in the workspace/ folder.
The lib/ folder contains LWJGL 3.2.3, JOML 1.10.2, JSON 2020-05-18.

Xahla framework provides several modules, that can be separately used:
- **Core**: 0.2.0 [Core engine]
- **Server** *(optional)*: 0.1.2 [Server-side features]
- **Render** *(optional)*: 0.1.3 [Client Rendering, supporting OpenGL and Vulkan]
- **Audio** *(optional)*: 0.1.1 [Sound & audio with OpenAL]
- **Input** *(optional)*: 0.1.3 [Keyboard, mouse and game controllers input handling]
- **Physics** *(optional)*: 0.1.2 [Physics engine]
- **GUI** *(optional)*: 0.1.1 [User-interface management]
- **AI** *(optional)*: 0.1.1 [Artifical Intelligence Handling]
- **VR** *(optional)*: 0.1.1 [Virtual Reality Handling]
- **Plugins** *(optional)*: 0.1.2 [Plugin & events manager for server-sided programs]
- **Database** *(optional)*: 0.1.1 [Database & API handler for web communication]

- **XMake**: 0.2.0 [Command-line tool for project management]
- **XProjectManager**: 0.1.2 [GUI for project management]

http://xahla.fr/framework - Get started with our framework
http://xahla.fr/api - Understand our web API