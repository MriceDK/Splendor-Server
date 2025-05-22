package be.howest.ti.game.web.views.response;

public class SpectateGameResponse extends JoinSpectateGameResponse {

    private final String token;
    private final String spectatorName;

    public SpectateGameResponse(int gameId, String spectatorName, String token) {
        super(gameId);
        this.token = token;
        this.spectatorName = spectatorName;
    }

    public String getSpectatorName() {
        return spectatorName;
    }

    public String getPlayerToken() {
        return token;
    }
}
