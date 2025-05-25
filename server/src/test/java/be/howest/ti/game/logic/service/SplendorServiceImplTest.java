package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.GameLobby;
import be.howest.ti.game.logic.Player;
import be.howest.ti.game.logic.PrivateGameLobby;
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
        service.createPrivateLobby(4, "John", CountryCode.BE, "game-03");

        service.removeGame(0);


        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findGame(0));
    }

    @Test
    void removeGames() {
        service.createLobby(4, "John", CountryCode.BE, "game-01");
        service.createLobby(4, "John", CountryCode.BE, "game-02");
        service.createPrivateLobby(4, "John", CountryCode.BE, "game-03");

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
    void removePlayerRemovedLobbyStillUp() {
        GameLobby lobby = service.createLobby(4, "Johnny", CountryCode.BE, "DoesGame");
        service.joinLobby(lobby, "Erikson", CountryCode.BE);
        service.joinLobby(lobby, "Melinoe", CountryCode.BE);

        service.removePlayer(lobby, "Erikson");

        assertEquals(2, service.findLobby(lobby.getGameId()).getTotalPlayers());
    }

    @Test
    void removePlayerLobbyRemains(){
        GameLobby lobby = service.createLobby(4, "Johnny", CountryCode.BE, "DoesGame");
        service.joinLobby(lobby, "Erikson", CountryCode.BE);
        service.joinLobby(lobby, "Melinoe", CountryCode.BE);

        service.removePlayer(lobby,"Erikson");
        service.removePlayer(lobby, "Melinoe");
        service.removePlayer(lobby, "Johnny");

        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findLobby(lobby.getGameId()));


    }
    @Test
    void createPrivateLobbyWithTwoParameters() {
        service.createPrivateLobby(2, "Alice", CountryCode.BE, "Password123");

        assertNull(service.getGames().getFirst().getGameName());
        assertEquals(2, service.getGames().getFirst().getMaxPlayers());
        assertEquals("Alice", service.getGames().getFirst().getPlayers().getFirst().getName());
    }

    @Test
    void createPrivateLobbyWithThreeParameters() {
        service.createPrivateLobby(3, "Alice", "Splendoras", CountryCode.BE, "Password123");

        assertEquals("Splendoras", service.getGames().getFirst().getGameName());
        assertEquals(3, service.getGames().getFirst().getMaxPlayers());
        assertEquals("Alice", service.getGames().getFirst().getPlayers().getFirst().getName());
    }

    @Test
    void startPrivateGameWhenLobbyIsFull() {
        PrivateGameLobby lobby = service.createPrivateLobby(3, "Jolien", CountryCode.BE, "Alohamora");

        service.joinLobby(lobby, "Riesje", CountryCode.BE, "Alohamora");
        service.joinLobby(lobby, "Leon", CountryCode.BE, "Alohamora");

        assertTrue(service.findGame(lobby.getGameId()).hasStarted());
    }

    @Test
    void privateGameDoesNotStartWhenNotFull() {
        PrivateGameLobby lobby = service.createPrivateLobby(4, "Edward", CountryCode.BE, "Twilight");

        service.joinLobby(lobby, "Bella", CountryCode.BE);
        service.joinLobby(lobby, "Jacob", CountryCode.BE);

        assertFalse(service.findGame(lobby.getGameId()).hasStarted());
    }

    @Test
    void testSpectatePublicLobby() {
        GameLobby lobby = service.createLobby(4, "SpectatorTest", CountryCode.BE);

        service.spectateLobby(lobby, "Spectator1");

        assertTrue(service.findGame(lobby.getGameId()).getSpectators().contains("Spectator1"));
    }

    @Test
    void testSpectatePrivateLobbyWithCorrectPassword() {
        PrivateGameLobby lobby = service.createPrivateLobby(4, "SpectatorTest", CountryCode.BE, "SecretPassword");

        service.spectateLobby(lobby, "Spectator1", "SecretPassword");

        assertTrue(service.findGame(lobby.getGameId()).getSpectators().contains("Spectator1"));
    }

    @Test
    void testSpectatePrivateLobbyWithWrongPassword() {
        PrivateGameLobby lobby = service.createPrivateLobby(4, "SpectatorTest", CountryCode.BE, "SecretPassword");

        assertThrows(IllegalArgumentException.class, () ->
            service.spectateLobby(lobby, "Spectator1", "WrongPassword")
        );
    }

    @Test
    void testSpectatePublicStartedGame() {
        GameLobby lobby = service.createLobby(4, "SpectatorTest", CountryCode.BE);
        service.joinLobby(lobby, "Player1", CountryCode.BE);
        service.joinLobby(lobby, "Player2", CountryCode.BE);
        service.joinLobby(lobby, "Player3", CountryCode.BE);

        // Start the game by filling the lobby
        assertTrue(service.findGame(lobby.getGameId()).hasStarted());

        service.spectateLobby(lobby, "Spectator1");

        assertTrue(service.findGame(lobby.getGameId()).getSpectators().contains("Spectator1"));
    }

    @Test
    void testSpectatePrivateStartedGame() {
        PrivateGameLobby lobby = service.createPrivateLobby(4, "SpectatorTest", CountryCode.BE, "SecretPassword");
        service.joinLobby(lobby, "Player1", CountryCode.BE, "SecretPassword");
        service.joinLobby(lobby, "Player2", CountryCode.BE, "SecretPassword");
        service.joinLobby(lobby, "Player3", CountryCode.BE, "SecretPassword");

        // Start the game by filling the lobby
        assertTrue(service.findGame(lobby.getGameId()).hasStarted());

        service.spectateLobby(lobby, "Spectator1", "SecretPassword");

        assertTrue(service.findGame(lobby.getGameId()).getSpectators().contains("Spectator1"));
    }

    @Test
    void testRemoveSpectator() {
        GameLobby lobby = service.createLobby(4, "SpectatorTest", CountryCode.BE);

        service.spectateLobby(lobby, "Spectator1");
        service.removeSpectator(lobby, "Spectator1");

        assertFalse(service.findGame(lobby.getGameId()).getSpectators().contains("Spectator1"));
    }

    @Test
    void testRemoveSpectatorNotInGame() {
        GameLobby lobby = service.createLobby(4, "SpectatorTest", CountryCode.BE);

        assertThrows(IllegalStateException.class, () ->
            service.removeSpectator(lobby, "NonExistentSpectator")
        );
    }

    @Test
    void testRemovePlayerFromGame() {
        GameLobby lobby = service.createLobby(4, "PlayerTest", CountryCode.BE);

        service.joinLobby(lobby, "Player1", CountryCode.BE);
        service.removePlayer(lobby, "Player1");

        assertFalse(service.findGame(lobby.getGameId()).getPlayers().contains(new Player("Player1")));
    }

    @Test
    void testRemovePlayerNotInGame() {
        GameLobby lobby = service.createLobby(4, "PlayerTest", CountryCode.BE);

        assertThrows(IllegalStateException.class, () ->
            service.removePlayer(lobby, "NonExistentPlayer")
        );
    }

}