package client;

import core.BaseEngineLogicInterface;

public interface GraphicEngineLogicInterface extends BaseEngineLogicInterface {

    default void onRender()         {}
    default void onPostRender()     {}

    default void onResize()         {}

}
