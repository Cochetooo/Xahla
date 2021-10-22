import templates.XH_ICoreLogic;
import utils.XH_LogLevel;

import static utils.XH_LoggerKt.logger;

public class JavaMainTest implements XH_ICoreLogic {

    public static void main(String[] args) {
        JavaMainTest main = new JavaMainTest();
        logger().setLogLevel(XH_LogLevel.ALL);
        XH_AppKt.app().build(XH_Context.class, main, 60);
        XH_AppKt.app().start();

        // Prohibited to do something here
    }

    @Override
    public void onAwake() {

    }

    @Override
    public void onPostInit() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void onInit() {

    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onPostUpdate() {

    }

    @Override
    public void onSecond() {
        logger().log("Hello world! ", XH_LogLevel.FINE, "JavaMainTest");
    }

    @Override
    public void onDispose() {

    }
}
