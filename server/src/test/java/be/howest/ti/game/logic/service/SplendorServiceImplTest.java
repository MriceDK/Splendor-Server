package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.GameLobby;
import be.howest.ti.game.logic.exceptions.SplendorGameResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SplendorServiceImplTest {

    private GameLobbyManager service;

    @BeforeEach
    void init() {
        service = new GameLobbyManager();
    }

    @Test
    void createLobbyWithTwoParameters() {
        service.createPublicLobby(2, "Yoni");

        assertNull(service.getGames().getFirst().getGameName());
        assertEquals(2, service.getGames().getFirst().getMaxPlayers());
        assertEquals("Yoni", service.getGames().getFirst().getPlayers().getFirst().getName());
    }

    @Test
    void createLobbyWithThreeParameters() {
        service.createPublicLobby(4, "John", "Epic Splendor Game");

        assertEquals("Epic Splendor Game", service.getGames().getFirst().getGameName());
        assertEquals(4, service.getGames().getFirst().getMaxPlayers());
        assertEquals("John", service.getGames().getFirst().getPlayers().getFirst().getName());
    }

    @Test
    void startGameWhenLobbyIsFull() {
        GameLobby lobby = service.createPublicLobby(4, "John");

        service.joinPublicLobby(lobby, "Eric");
        service.joinPublicLobby(lobby, "Steve");
        service.joinPublicLobby(lobby, "Alice");

        assertTrue(service.findGame(lobby.getGameId()).hasStarted());
    }

    @Test
    void gameDoesNotStartWhenNotFull() {
        GameLobby lobby = service.createPublicLobby(4, "John");

        service.joinPublicLobby(lobby, "Eric");
        service.joinPublicLobby(lobby, "Steve");

        assertFalse(service.findGame(lobby.getGameId()).hasStarted());
    }

    @Test
    void removeGame() {
        service.createPublicLobby(4, "John", "game-01");
        service.createPublicLobby(4, "John", "game-02");

        service.removeGame(0);


        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findGame(0));
    }

    @Test
    void removeGames() {
        service.createPublicLobby(4, "John", "game-01");
        service.createPublicLobby(4, "John", "game-02");

        service.removeGames();

        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findGame(0));
    }

    @Test
    void gameIdGeneratesCorrectly() {
        service.createPublicLobby(4, "John", "game-01");
        service.createPublicLobby(4, "John", "game-02");

        service.removeGame(1);

        service.createPublicLobby(4, "John", "game-03");

        assertEquals("game-03", service.findGame(2).getGameName());
    }

}