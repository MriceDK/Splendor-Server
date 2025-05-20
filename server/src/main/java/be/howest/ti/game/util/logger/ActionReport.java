package be.howest.ti.game.util.logger;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ActionReport {

    private final String playerName;
    private final String action;
    private final LocalTime timeOfCreation;

    public ActionReport(String playerName, String action) {
        this.playerName = playerName;
        this.action = action;
        timeOfCreation = LocalTime.now();
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getAction() {
        return action;
    }

    public LocalTime getTimeOfCreation() {
        return timeOfCreation;
    }

    public String getFormattedTimeOfCreation() {
        return timeOfCreation.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
