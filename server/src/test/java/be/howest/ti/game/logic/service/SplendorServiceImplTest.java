package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.GameLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SplendorServiceImplTest {

    private SplendorServiceImpl service;

    @BeforeEach
    public void init() {
        service = new SplendorServiceImpl();
    }

    @Test
    public void createLobbyWithTwoParameters() {
        service.createLobby(2, "Yoni");

        assertNull(service.getGames().getFirst().getGameName());
        assertEquals(2, service.getGames().getFirst().getMaxPlayers());
        assertEquals("Yoni", service.getGames().getFirst().getPlayers().getFirst().getName());
    }

    @Test
    public void createLobbyWithThreeParameters() {
        service.createLobby(4, "John", "Epic Splendor Game");

        assertEquals("Epic Splendor Game", service.getGames().getFirst().getGameName());
        assertEquals(4, service.getGames().getFirst().getMaxPlayers());
        assertEquals("John", service.getGames().getFirst().getPlayers().getFirst().getName());
    }

    @Test
    public void startGameWhenLobbyIsFull() {
        GameLobby lobby = service.createLobby(4, "John");

        service.joinLobby(lobby, "Eric");
        service.joinLobby(lobby, "Steve");
        service.joinLobby(lobby, "Alice");

        assertTrue(service.findGame(lobby.getGameId()).hasStarted());
    }

    @Test
    public void gameDoesNotStartWhenNotFull() {
        GameLobby lobby = service.createLobby(4, "John");

        service.joinLobby(lobby, "Eric");
        service.joinLobby(lobby, "Steve");

        assertFalse(service.findGame(lobby.getGameId()).hasStarted());
    }

    @Test
    public void removeGame() {
        service.createLobby(4, "John", "game-01");
        service.createLobby(4, "John", "game-02");

        service.removeGame(0);


        assertThrows(IllegalArgumentException.class, () -> service.findGame(0));
    }

    @Test
    public void removeGames() {
        service.createLobby(4, "John", "game-01");
        service.createLobby(4, "John", "game-02");

        service.removeGames();

        assertThrows(IllegalArgumentException.class, () -> service.findGame(0));
    }

    @Test
    public void gameIdGeneratesCorrectly() {
        service.createLobby(4, "John", "game-01");
        service.createLobby(4, "John", "game-02");

        service.removeGame(1);

        service.createLobby(4, "John", "game-03");

        assertEquals("game-03", service.findGame(2).getGameName());
    }

}