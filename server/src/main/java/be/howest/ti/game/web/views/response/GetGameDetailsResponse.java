package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.GameLobby;
import be.howest.ti.game.logic.GameSuperclass;
import be.howest.ti.game.logic.SplendorGame;

public class GetGameDetailsResponse extends AbstractResponseWithHiddenStatus {

    private final GameSuperclass game;

    public GetGameDetailsResponse(GameSuperclass game) {
        super(200);
        this.game = game;
    }

    public SplendorGame startedGame() {
        return (SplendorGame) game;
    }

    public GameLobby unstartedGame() {
        return (GameLobby) game;
    }

    public int getGameId() {
        return game.getGameId();
    }

    public String getGameName() {
        return game.getGameName();
    }

    public boolean getStarted() {
        return game.hasStarted();
    }

    public int getNumberOfPlayers() {
        return game.getMaxPlayers();
    }

}
