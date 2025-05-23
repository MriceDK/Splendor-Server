package be.howest.ti.game.util.logger;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {

    @Test
    void logShouldAddALog() {
        Logger logger = new Logger();

        ActionReport actionReport = new ActionReport("Alice", "took 2 gems");
        logger.log(actionReport);

        assertEquals(List.of(actionReport), logger.getLogs());
    }

}