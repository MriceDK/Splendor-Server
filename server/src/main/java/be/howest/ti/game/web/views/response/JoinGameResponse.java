package be.howest.ti.game.web.views.response;

public class JoinGameResponse extends AbstractResponseWithHiddenStatus {
    private final int gameId;
    private final String playerName;

    public JoinGameResponse(int gameId, String playerName) {
        super(200);
        this.gameId = gameId;
        this.playerName = playerName;
    }

    public int getGameId() {
        return gameId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerToken() {
        return getGameId() + "_" + getPlayerName();
    }
}
