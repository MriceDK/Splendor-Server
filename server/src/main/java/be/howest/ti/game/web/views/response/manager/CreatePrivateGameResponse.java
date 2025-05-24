package be.howest.ti.game.web.views.response.manager;

import be.howest.ti.game.logic.GameLobby;

public class CreatePrivateGameResponse extends CreateGameResponse{
    private final String password;

    public CreatePrivateGameResponse(GameLobby game, String playerName, String token, String password) {
        super(game, playerName, token);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
