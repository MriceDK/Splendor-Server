package be.howest.ti.game.web.views.response.manager;

import be.howest.ti.game.logic.GameLobby;
import be.howest.ti.game.util.customization.CountryCode;
import be.howest.ti.game.web.views.response.AbstractResponseWithHiddenStatus;

public class CreateGameResponse extends AbstractResponseWithHiddenStatus {

    private final GameLobby game;
    private final String playerName;
    private final String token;
    private final CountryCode avatar;

    public CreateGameResponse(GameLobby game, String playerName, String token, CountryCode avatar) {
        super(200);
        this.game = game;
        this.playerName = playerName;
        this.token = token;
        this.avatar = avatar;
    }

    public int getGameId() {
        return game.getGameId();
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerToken() {
        return token;
    }

    public CountryCode getAvatar(){
        return avatar;
    }
}