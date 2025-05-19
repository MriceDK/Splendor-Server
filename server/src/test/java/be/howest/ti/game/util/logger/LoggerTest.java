package be.howest.ti.game.util.logger;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {

    @Test
    public void logShouldAddALog() {
        Logger logger = new Logger();

        logger.log("Alice took 2 gems");

        assertEquals(List.of("Alice took 2 gems"), logger.getLogs());
    }

}