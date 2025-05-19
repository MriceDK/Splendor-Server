package be.howest.ti.game.web.views.response;

public class JoinGameResponse extends JoinSpectateGameResponse {

    private final String token;
    private final String playerName;

    public JoinGameResponse(int gameId, String playerName, String token) {
        super(gameId);
        this.playerName = playerName;
        this.token = token;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerToken() {
        return token;
    }
}
