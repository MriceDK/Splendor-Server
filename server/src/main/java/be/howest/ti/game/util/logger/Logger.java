package be.howest.ti.game.util.logger;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    private final List<ActionReport> logs;

    public Logger() {
        logs = new ArrayList<>();
    }

    public void log(ActionReport actionReport) {
        logs.add(actionReport);
    }

    public List<ActionReport> getLogs() {
        return logs;
    }

}
