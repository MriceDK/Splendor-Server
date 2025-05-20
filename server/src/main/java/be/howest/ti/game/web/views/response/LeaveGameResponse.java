package be.howest.ti.game.web.views.response;

public class LeaveGameResponse extends JoinSpectateGameResponse {

    private final String playerName;

    public LeaveGameResponse(int gameId, String playerName) {
        super(gameId);

        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
    public String getAction() {
        return "leaving game/lobby";
    }
}
