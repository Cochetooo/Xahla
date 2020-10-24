# Xahla
The Efficient Workspace for Java Video Game Developpers.

Xahla is a framework that greatly ease the development of video games in Java by having its own automated workspace.
With our product, you'll be able to design:

→ OpenGL based projects in Java with an Entity-Component System (soon Vulkan and DirectX will be supported)

→ Software based for GUI projects in Java and XML with a Model View View-model Architecture

→ Server Side based projects in Java with a Plugin Handler with an Event-Driven Architecture

The workspace includes a manager for JSON API relations by HTTP Request (2.0).
It also includes XMake, a command-line executable that add a bundle of utility commands such as project creation, or auto-generated class.

# Install
To use our workspace, you will need Java Runtime Environment 14+ and Python 3.9+ installed on your computer.
Then, all you have to do is download the zipped project and start a project by making one of the following command on the root directory:

```
py XMake/make.py create:xahla-core {gameName}
py XMake/make.py create:xahla-gui {softwareName}
py XMake/make.py create:xahla-server {serverName}
py XMake/make.py create:xahla-plugin {pluginName} {serverProjectName}
```

All your projects are stored in the workspace/ folder.
The lib/ folder contains LWJGL 3.2.3, JOML 1.9.25, JSON 2020-05-18, Xahla-Core 1.0 and Xahla-Utils 1.1
