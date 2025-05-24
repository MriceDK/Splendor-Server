package be.howest.ti.game.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    @Test
    void toDisplayName() {
        GameState gameState = GameState.TURN_ACTION;

        assertEquals("TurnAction", gameState.toDisplayName());
    }

}