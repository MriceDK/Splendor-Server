package be.howest.ti.game.util.logger;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    private final List<String> logs;

    public Logger() {
        logs = new ArrayList<>();
    }

    public void log(String message) {
        logs.add(message);
    }

    public List<String> getLogs() {
        return logs;
    }

}
