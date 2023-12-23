package core;

public interface BaseEngineLogicInterface {

    default void onAwake()      {}
    default void onInit()       {}

    default void onUpdate()     {}
    default void onPostUpdate() {}

    default void onSecond()     {}

    default void onPause()      {}
    default void onResume()     {}

    default void onDispose()    {}
    default void onExit()       {}

}
