package test;

import org.joml.Vector2i;
import org.xahla.client.VGCClientApp;
import org.xahla.client.platform.domain.enums.VGCPlatformApiEnum;
import org.xahla.client.platform.domain.model.VGCImage;
import org.xahla.client.rendering.domain.enums.VGCRenderingApiEnum;
import org.xahla.client.rendering.domain.repository.VGCGraphicEngineLogicInterface;

import java.util.logging.*;

public class Main implements VGCGraphicEngineLogicInterface {

    private VGCClientApp app;
    private Logger logger;

    public static void main(String[] args) {
        Main main = new Main();

        main.logger = Logger.getLogger("main");
        main.logger.setLevel(Level.ALL);

        var consoleHandler = new StreamHandler(System.out, new SimpleFormatter());
        consoleHandler.setLevel(Level.ALL);

        main.logger.addHandler(consoleHandler);
        main.logger.setUseParentHandlers(false);

        main.app = new VGCClientApp(
            main,
            60,
            -1,
            main.logger
        );

        main.app.createClientContext(
            VGCRenderingApiEnum.NONE,
            VGCPlatformApiEnum.GLFW,
            null
        );

        main.app.run();
    }

    @Override public void onInit() {
        var window = this.app.getContext().getWindow();

        window.resize(new Vector2i(500, 400));
        window.setSizeBounds(new Vector2i(300, 300), new Vector2i(700, 700));
        window.setPosition(new Vector2i(600, 100));
        window.setTitle("Hihi");
        window.setIcon(new VGCImage(
            2, 2, new byte[]{
            (byte) 200, (byte) 200, (byte) 200, (byte) 200
        }));
        window.setOpacity(0.25f);
        window.setFocusOnShow(true);
    }

    @Override public void onSecond() {
        this.app.getContext().getWindow().setTitle(
            STR."UPS: \{this.app.getCurrentTickRate()} | FPS: \{this.app.getCurrentFrameRate()}"
        );
    }

}
