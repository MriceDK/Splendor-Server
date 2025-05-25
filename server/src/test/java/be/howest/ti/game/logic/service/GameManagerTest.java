package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.GameLobby;
import be.howest.ti.game.logic.exceptions.SplendorGameResourceNotFoundException;
import be.howest.ti.game.util.customization.CountryCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

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
        service.createLobby(4, "John", CountryCode.BE, "Epic Splendor Game");

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

    @Test
    void findLobby() {
        service.createLobby(4, "John", CountryCode.BE, "game-01");
        service.createLobby(4, "John", CountryCode.BE, "game-02");

        assertEquals("game-01", service.findLobby(0).getGameName());
        assertEquals("game-02", service.findLobby(1).getGameName());
    }

    @Test
    void findStartedGame() {
        service.createLobby(4, "John", CountryCode.BE, "game-01");
        service.createLobby(4, "John", CountryCode.BE, "game-02");

        service.startGame(service.findLobby(0));

        assertEquals("game-01", service.findStartedGame(0).getGameName());
        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findStartedGame(1));
    }

    @Test
    void findGame() {
        service.createLobby(4, "John", CountryCode.BE, "game-01");
        service.createLobby(4, "John", CountryCode.BE, "game-02");

        service.startGame(service.findLobby(0));

        assertEquals("game-01", service.findGame(0).getGameName());
        assertEquals("game-02", service.findGame(1).getGameName());
    }

    @Test
    void findGameNotFound() {
        service.createLobby(4, "John", CountryCode.BE, "game-01");
        service.createLobby(4, "John", CountryCode.BE, "game-02");

        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findGame(2));
    }

    @Test
    void findLobbyNotFound() {
        service.createLobby(4, "John", CountryCode.BE, "game-01");
        service.createLobby(4, "John", CountryCode.BE, "game-02");

        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findLobby(2));
    }

    @Test
    void findStartedGameNotFound() {
        service.createLobby(4, "John", CountryCode.BE, "game-01");
        service.createLobby(4, "John", CountryCode.BE, "game-02");

        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findStartedGame(2));
    }

    @Test
    void addSpectator() {
        service.createLobby(4, "John", CountryCode.BE, "game-01");
        service.createLobby(4, "John", CountryCode.BE, "game-02");

        service.spectateLobby(service.findLobby(0), "Spectator1");

        assertEquals("Spectator1", service.findGame(0).getSpectators().getFirst());
        assertTrue(service.findGame(1).getSpectators().isEmpty());
    }

    @Test
    void removeSpectator() {
        service.createLobby(4, "John", CountryCode.BE, "game-01");
        service.createLobby(4, "John", CountryCode.BE, "game-02");

        service.spectateLobby(service.findLobby(0), "Spectator1");
        service.removeSpectator(service.findLobby(0), "Spectator1");

        assertTrue(service.findGame(0).getSpectators().isEmpty());
    }

    @Test
    void removeSpectatorNotFound() {
        service.createLobby(4, "John", CountryCode.BE, "game-01");
        service.createLobby(4, "John", CountryCode.BE, "game-02");

        service.spectateLobby(service.findLobby(0), "Spectator1");
        service.removeSpectator(service.findLobby(0), "Spectator1");

        assertThrows(IllegalStateException.class, () -> service.removeSpectator(service.findLobby(0), "Spectator1"));
    }

    @Test
    void leaveGame() {
        service.createLobby(4, "John", CountryCode.BE, "game-01");
        service.createLobby(4, "John", CountryCode.BE, "game-02");

        service.joinLobby(service.findLobby(0), "AliceInChains", CountryCode.BE);
        service.removePlayer(service.findLobby(0), "AliceInChains");

        assertEquals(1, service.findGame(0).getPlayers().size());
    }

    @Test
    void leaveGameNotFound() {
        service.createLobby(4, "John", CountryCode.BE, "game-01");
        service.createLobby(4, "John", CountryCode.BE, "game-02");

        service.joinLobby(service.findLobby(0), "AliceInChains", CountryCode.BE);
        service.removePlayer(service.findLobby(0), "AliceInChains");

        assertThrows(IllegalStateException.class, () -> service.removePlayer(service.findLobby(0), "AliceInChains"));
    }

    @Test
    void joinLobby() {
        service.createLobby(4, "John", CountryCode.BE, "game-01");
        service.createLobby(4, "John", CountryCode.BE, "game-02");

        service.joinLobby(service.findLobby(0), "AliceInChains", CountryCode.BE);

        assertEquals(2, service.findGame(0).getPlayers().size());
        assertEquals(1, service.findGame(1).getPlayers().size());
    }

}