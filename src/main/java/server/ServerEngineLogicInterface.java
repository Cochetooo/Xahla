package server;

public interface ServerEngineLogicInterface {

    default void onClientUpdate()       {}

    default void onServerUpdate()       {}

}
