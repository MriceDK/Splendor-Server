package be.howest.ti.game.web.views.response;

public class LeaveGameResponse extends JoinSpectateGameResponse {

    private final String playerName;
    private final boolean gameStarted;

    public LeaveGameResponse(int gameId, String playerName, boolean GameStarted) {
        super(gameId);
        this.gameStarted = GameStarted;
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getAction() {
        if (gameStarted) {
            return "leaving game";
        } else {
            return "leaving lobby";
        }

    }
}
