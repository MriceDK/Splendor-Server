package be.howest.ti.game.util.logger;

public class ActionReport {

    private final String playerName;
    private final String action;

    public ActionReport(String playerName, String action) {
        this.playerName = playerName;
        this.action = action;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getAction() {
        return action;
    }
}
