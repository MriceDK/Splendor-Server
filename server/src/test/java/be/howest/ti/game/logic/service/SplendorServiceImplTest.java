package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.GameLobby;
import be.howest.ti.game.logic.exceptions.SplendorGameResourceNotFoundException;
import be.howest.ti.game.util.customization.CountryCode;
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
        service.createLobby(2, "Yoni", CountryCode.BE);

        assertNull(service.getGames().getFirst().getGameName());
        assertEquals(2, service.getGames().getFirst().getMaxPlayers());
        assertEquals("Yoni", service.getGames().getFirst().getPlayers().getFirst().getName());
    }

    @Test
    void createLobbyWithThreeParameters() {
        service.createLobby(4, "John", CountryCode.BE, "Epic Splendor Game", );

        assertEquals("Epic Splendor Game", service.getGames().getFirst().getGameName());
        assertEquals(4, service.getGames().getFirst().getMaxPlayers());
        assertEquals("John", service.getGames().getFirst().getPlayers().getFirst().getName());
    }

    @Test
    void startGameWhenLobbyIsFull() {
        GameLobby lobby = service.createLobby(4, "John", CountryCode.BE);

        service.joinLobby(lobby, "Eric", CountryCode.BE);
        service.joinLobby(lobby, "Steve", CountryCode.BE);
        service.joinLobby(lobby, "Alice", CountryCode.BE);

        assertTrue(service.findGame(lobby.getGameId()).hasStarted());
    }

    @Test
    void gameDoesNotStartWhenNotFull() {
        GameLobby lobby = service.createLobby(4, "John", CountryCode.BE);

        service.joinLobby(lobby, "Eric", CountryCode.BE);
        service.joinLobby(lobby, "Steve", CountryCode.BE);

        assertFalse(service.findGame(lobby.getGameId()).hasStarted());
    }

    @Test
    void removeGame() {
        service.createLobby(4, "John", CountryCode.BE, "game-01");
        service.createLobby(4, "John", CountryCode.BE, "game-02");

        service.removeGame(0);


        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findGame(0));
    }

    @Test
    void removeGames() {
        service.createLobby(4, "John", CountryCode.BE, "game-01");
        service.createLobby(4, "John", CountryCode.BE, "game-02");

        service.removeGames();

        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findGame(0));
    }

    @Test
    void gameIdGeneratesCorrectly() {
        service.createLobby(4, "John", CountryCode.BE, "game-01");
        service.createLobby(4, "John", CountryCode.BE, "game-02");

        service.removeGame(1);

        service.createLobby(4, "John", CountryCode.BE, "game-03");

        assertEquals("game-03", service.findGame(2).getGameName());
    }

}