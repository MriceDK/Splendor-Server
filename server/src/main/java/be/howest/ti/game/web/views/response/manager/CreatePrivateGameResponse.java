package be.howest.ti.game.web.views.response.manager;

import be.howest.ti.game.logic.GameLobby;
import be.howest.ti.game.util.customization.CountryCode;

public class CreatePrivateGameResponse extends CreateGameResponse{
    private final String password;

    public CreatePrivateGameResponse(GameLobby game, String playerName, String token, String password, CountryCode avatar) {
        super(game, playerName, token, avatar);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
