package be.howest.ti.game.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SplendorGameTest {

    private GameSuperclass lobby;

    @BeforeEach
    public void init() {
        lobby = new GameLobby(1, 4);
    }

    @Test
    public void copyConstructor() {
        lobby.addPlayer("Bobby");
        SplendorGame game = new SplendorGame(lobby);
        assertEquals(1, game.getGameId());
        assertNull(game.getGameName());
        assertEquals(1, game.getTotalPlayers());
    }

    @Test
    public void reserveDevelopment() {
        lobby.addPlayer("Bobby");
        SplendorGame startedGame = new SplendorGame(lobby);
        startedGame.reserveDevelopment("Development 1");
        // TODO: Change this when the actual Development cards are implemented
        // assertEquals uses an exact copy of the Development object that was reserved
        // haven't figured out how to get this any other way
        assertEquals(new Development("Development 1", 1, 1, null, null),
                startedGame.getCurrentPlayer().getReservedCards().getFirst());
        assertFalse(startedGame.getMarket().getVisibleDevelopments(1).contains(new Development("Development 1", 1, 1, null, null)));
    }

    @Test
    public void reserveDevelopmentFromLevel() {
        lobby.addPlayer("Bobby");
        SplendorGame startedGame = new SplendorGame(lobby);
        startedGame.reserveDevelopmentFromLevel(1);
        // TODO: Change this when the actual Development cards are implemented
        // assertTrue uses an exact copy of the Development object that was reserved
        // haven't figured out how to get this any other way
        assertTrue(startedGame.getCurrentPlayer().getReservedCards().contains(new Development("Development 5", 1, 5, null, null)));
    }

}