module XahlaFramework.main {

    requires org.joml;
    requires org.lwjgl.glfw;
    requires org.lwjgl.vulkan;
    requires annotations;

    exports client;
    exports core;
    exports server;

    exports utils.locales;
    exports utils.logging;

}