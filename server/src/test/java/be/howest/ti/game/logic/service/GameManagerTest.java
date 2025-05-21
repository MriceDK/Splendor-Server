package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.GameLobby;
import be.howest.ti.game.logic.exceptions.SplendorGameResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    private GameLobbyManager service;

    @BeforeEach
    public void init() {
        service = new GameLobbyManager();
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


        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findGame(0));
    }

    @Test
    public void removeGames() {
        service.createLobby(4, "John", "game-01");
        service.createLobby(4, "John", "game-02");

        service.removeGames();

        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findGame(0));
    }

    @Test
    public void gameIdGeneratesCorrectly() {
        service.createLobby(4, "John", "game-01");
        service.createLobby(4, "John", "game-02");

        service.removeGame(1);

        service.createLobby(4, "John", "game-03");

        assertEquals("game-03", service.findGame(2).getGameName());
    }

    @Test
    public void findLobby() {
        service.createLobby(4, "John", "game-01");
        service.createLobby(4, "John", "game-02");

        assertEquals("game-01", service.findLobby(0).getGameName());
        assertEquals("game-02", service.findLobby(1).getGameName());
    }

    @Test
    public void findStartedGame() {
        service.createLobby(4, "John", "game-01");
        service.createLobby(4, "John", "game-02");

        service.startGame(service.findLobby(0));

        assertEquals("game-01", service.findStartedGame(0).getGameName());
        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findStartedGame(1));
    }

    @Test
    public void findGame() {
        service.createLobby(4, "John", "game-01");
        service.createLobby(4, "John", "game-02");

        service.startGame(service.findLobby(0));

        assertEquals("game-01", service.findGame(0).getGameName());
        assertEquals("game-02", service.findGame(1).getGameName());
    }

    @Test
    public void findGameNotFound() {
        service.createLobby(4, "John", "game-01");
        service.createLobby(4, "John", "game-02");

        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findGame(2));
    }

    @Test
    public void findLobbyNotFound() {
        service.createLobby(4, "John", "game-01");
        service.createLobby(4, "John", "game-02");

        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findLobby(2));
    }

    @Test
    public void findStartedGameNotFound() {
        service.createLobby(4, "John", "game-01");
        service.createLobby(4, "John", "game-02");

        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findStartedGame(2));
    }

    @Test
    public void addSpectator() {
        service.createLobby(4, "John", "game-01");
        service.createLobby(4, "John", "game-02");

        service.spectateLobby(service.findLobby(0), "Spectator1");

        assertEquals("Spectator1", service.findGame(0).getSpectators().getFirst());
        assertTrue(service.findGame(1).getSpectators().isEmpty());
    }

    @Test
    public void removeSpectator() {
        service.createLobby(4, "John", "game-01");
        service.createLobby(4, "John", "game-02");

        service.spectateLobby(service.findLobby(0), "Spectator1");
        service.leaveSpectate(service.findLobby(0), "Spectator1");

        assertTrue(service.findGame(0).getSpectators().isEmpty());
    }

    @Test
    public void removeSpectatorNotFound() {
        service.createLobby(4, "John", "game-01");
        service.createLobby(4, "John", "game-02");

        service.spectateLobby(service.findLobby(0), "Spectator1");
        service.leaveSpectate(service.findLobby(0), "Spectator1");

        assertThrows(IllegalStateException.class, () -> service.leaveSpectate(service.findLobby(0), "Spectator1"));
    }

    @Test
    public void leaveGame() {
        service.createLobby(4, "John", "game-01");
        service.createLobby(4, "John", "game-02");

        service.joinLobby(service.findLobby(0), "AliceInChains");
        service.leaveGame(service.findLobby(0), "AliceInChains");

        assertEquals(1, service.findGame(0).getPlayers().size());
    }

    @Test
    public void leaveGameNotFound() {
        service.createLobby(4, "John", "game-01");
        service.createLobby(4, "John", "game-02");

        service.joinLobby(service.findLobby(0), "AliceInChains");
        service.leaveGame(service.findLobby(0), "AliceInChains");

        assertThrows(IllegalStateException.class, () -> service.leaveGame(service.findLobby(0), "AliceInChains"));
    }

    @Test
    public void joinLobby() {
        service.createLobby(4, "John", "game-01");
        service.createLobby(4, "John", "game-02");

        service.joinLobby(service.findLobby(0), "AliceInChains");

        assertEquals(2, service.findGame(0).getPlayers().size());
        assertEquals(1, service.findGame(1).getPlayers().size());
    }

}