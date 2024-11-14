package ui;

import net.ServerFacade;

public class gameClient {
    ServerFacade serverFacade;

    public gameClient(ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
    }

    public String run(String command) {
        throw new RuntimeException("GameClient not implemented yet");
    }
}
