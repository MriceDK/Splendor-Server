package be.howest.ti.game.web.views.response.manager;

import be.howest.ti.game.util.customization.CountryCode;

public class JoinGameResponse extends JoinSpectateGameResponse {

    private final String token;
    private final String playerName;
    private final CountryCode avatar;

    public JoinGameResponse(int gameId, String playerName, String token, CountryCode avatar) {
        super(gameId);
        this.playerName = playerName;
        this.token = token;
        this.avatar = avatar;
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
