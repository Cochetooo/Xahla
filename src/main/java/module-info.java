module VGCodingExt.main {

    requires org.joml;
    requires org.lwjgl.glfw;
    requires org.lwjgl.openal;
    requires org.lwjgl.vulkan;
    requires org.lwjgl.opengl;

    requires io.soabase.recordbuilder.core;
    requires java.compiler;
    requires java.desktop;
    requires java.management;
    requires java.logging;

    exports org.xahla.core;

    exports org.xahla.utils.system;
    exports org.xahla.core.repository;

}