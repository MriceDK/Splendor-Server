package be.howest.ti.game.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SplendorGameTest {

    private GameSuperclass lobby;

    @BeforeEach
    public void init() {
        lobby = new GameLobby(1, 4);
    }

    @Test
    public void copyConstructor() {
        GameSuperclass game = new SplendorGame(lobby);

        assertEquals(1, game.getGameId());
        assertNull(game.getGameName());
        assertEquals(0, game.getTotalPlayers());
    }

}