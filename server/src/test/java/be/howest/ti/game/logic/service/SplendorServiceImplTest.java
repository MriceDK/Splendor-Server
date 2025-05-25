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
        service.createPublicLobby(2, "Yoni", CountryCode.BE);

        assertNull(service.getGames().getFirst().getGameName());
        assertEquals(2, service.getGames().getFirst().getMaxPlayers());
        assertEquals("Yoni", service.getGames().getFirst().getPlayers().getFirst().getName());
    }

    @Test
    void createLobbyWithThreeParameters() {
        service.createPublicLobby(4, "John", CountryCode.BE, "Epic Splendor Game");

        assertEquals("Epic Splendor Game", service.getGames().getFirst().getGameName());
        assertEquals(4, service.getGames().getFirst().getMaxPlayers());
        assertEquals("John", service.getGames().getFirst().getPlayers().getFirst().getName());
    }

    @Test
    void startGameWhenLobbyIsFull() {
        GameLobby lobby = service.createPublicLobby(4, "John", CountryCode.BE);

        service.joinLobby(lobby, "Eric", CountryCode.BE);
        service.joinLobby(lobby, "Steve", CountryCode.BE);
        service.joinLobby(lobby, "Alice", CountryCode.BE);

        assertTrue(service.findGame(lobby.getGameId()).hasStarted());
    }

    @Test
    void gameDoesNotStartWhenNotFull() {
        GameLobby lobby = service.createPublicLobby(4, "John", CountryCode.BE);

        service.joinLobby(lobby, "Eric", CountryCode.BE);
        service.joinLobby(lobby, "Steve", CountryCode.BE);

        assertFalse(service.findGame(lobby.getGameId()).hasStarted());
    }

    @Test
    void removeGame() {
        service.createPublicLobby(4, "John", CountryCode.BE, "game-01");
        service.createPublicLobby(4, "John", CountryCode.BE, "game-02");
        service.createPrivateLobby(4, "John", CountryCode.BE, "game-03");

        service.removeGame(0);


        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findGame(0));
    }

    @Test
    void removeGames() {
        service.createPublicLobby(4, "John", CountryCode.BE, "game-01");
        service.createPublicLobby(4, "John", CountryCode.BE, "game-02");
        service.createPrivateLobby(4, "John", CountryCode.BE, "game-03");

        service.removeGames();

        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findGame(0));
    }

    @Test
    void gameIdGeneratesCorrectly() {
        service.createPublicLobby(4, "John", CountryCode.BE, "game-01");
        service.createPublicLobby(4, "John", CountryCode.BE, "game-02");

        service.removeGame(1);

        service.createPublicLobby(4, "John", CountryCode.BE, "game-03");

        assertEquals("game-03", service.findGame(2).getGameName());
    }

    @Test
    void removePlayerRemovedLobbyStillUp() {
        GameLobby lobby = service.createPublicLobby(4, "Johnny", CountryCode.BE, "DoesGame");
        service.joinLobby(lobby, "Erikson", CountryCode.BE);
        service.joinLobby(lobby, "Melinoe", CountryCode.BE);

        service.removePlayer(lobby, "Erikson");

        assertEquals(2, service.findLobby(lobby.getGameId()).getTotalPlayers());
    }

    @Test
    void removePlayerLobbyRemains(){
        GameLobby lobby = service.createPublicLobby(4, "Johnny", CountryCode.BE, "DoesGame");
        service.joinLobby(lobby, "Erikson", CountryCode.BE);
        service.joinLobby(lobby, "Melinoe", CountryCode.BE);

        service.removePlayer(lobby,"Erikson");
        service.removePlayer(lobby, "Melinoe");
        service.removePlayer(lobby, "Johnny");

        assertThrows(SplendorGameResourceNotFoundException.class, () -> service.findLobby(lobby.getGameId()));


    }
    @Test
    void createPrivateLobbyWithTwoParameters() {
        service.createPrivateLobby(2, "Alice", "Password123");

        assertNull(service.getGames().getFirst().getGameName());
        assertEquals(2, service.getGames().getFirst().getMaxPlayers());
        assertEquals("Alice", service.getGames().getFirst().getPlayers().getFirst().getName());
    }

    @Test
    void createPrivateLobbyWithThreeParameters() {
        service.createPrivateLobby(3, "Alice", "Splendoras", "Password123");

        assertEquals("Splendoras", service.getGames().getFirst().getGameName());
        assertEquals(3, service.getGames().getFirst().getMaxPlayers());
        assertEquals("Alice", service.getGames().getFirst().getPlayers().getFirst().getName());
    }

    @Test
    void startPrivateGameWhenLobbyIsFull() {
        PrivateGameLobby lobby = service.createPrivateLobby(3, "Jolien", "Alohamora");

        service.joinLobby(lobby, "Riesje", "Alohamora");
        service.joinLobby(lobby, "Leon", "Alohamora");

        assertTrue(service.findGame(lobby.getGameId()).hasStarted());
    }

    @Test
    void privateGameDoesNotStartWhenNotFull() {
        PrivateGameLobby lobby = service.createPrivateLobby(4, "Edward", "Twilight");

        service.joinLobby(lobby, "Bella");
        service.joinLobby(lobby, "Jacob");

        assertFalse(service.findGame(lobby.getGameId()).hasStarted());
    }

    @Test
    void testSpectatePublicLobby() {
        GameLobby lobby = service.createPublicLobby(4, "SpectatorTest");

        service.spectateLobby(lobby, "Spectator1");

        assertTrue(service.findGame(lobby.getGameId()).getSpectators().contains("Spectator1"));
    }

    @Test
    void testSpectatePrivateLobbyWithCorrectPassword() {
        PrivateGameLobby lobby = service.createPrivateLobby(4, "SpectatorTest", "SecretPassword");

        service.spectateLobby(lobby, "Spectator1", "SecretPassword");

        assertTrue(service.findGame(lobby.getGameId()).getSpectators().contains("Spectator1"));
    }

    @Test
    void testSpectatePrivateLobbyWithWrongPassword() {
        PrivateGameLobby lobby = service.createPrivateLobby(4, "SpectatorTest", "SecretPassword");

        assertThrows(IllegalArgumentException.class, () ->
            service.spectateLobby(lobby, "Spectator1", "WrongPassword")
        );
    }

    @Test
    void testSpectatePublicStartedGame() {
        GameLobby lobby = service.createPublicLobby(4, "SpectatorTest");
        service.joinLobby(lobby, "Player1");
        service.joinLobby(lobby, "Player2");
        service.joinLobby(lobby, "Player3");

        // Start the game by filling the lobby
        assertTrue(service.findGame(lobby.getGameId()).hasStarted());

        service.spectateLobby(lobby, "Spectator1");

        assertTrue(service.findGame(lobby.getGameId()).getSpectators().contains("Spectator1"));
    }

    @Test
    void testSpectatePrivateStartedGame() {
        PrivateGameLobby lobby = service.createPrivateLobby(4, "SpectatorTest", "SecretPassword");
        service.joinLobby(lobby, "Player1", "SecretPassword");
        service.joinLobby(lobby, "Player2", "SecretPassword");
        service.joinLobby(lobby, "Player3", "SecretPassword");

        // Start the game by filling the lobby
        assertTrue(service.findGame(lobby.getGameId()).hasStarted());

        service.spectateLobby(lobby, "Spectator1", "SecretPassword");

        assertTrue(service.findGame(lobby.getGameId()).getSpectators().contains("Spectator1"));
    }

    @Test
    void testRemoveSpectator() {
        GameLobby lobby = service.createPublicLobby(4, "SpectatorTest");

        service.spectateLobby(lobby, "Spectator1");
        service.removeSpectator(lobby, "Spectator1");

        assertFalse(service.findGame(lobby.getGameId()).getSpectators().contains("Spectator1"));
    }

    @Test
    void testRemoveSpectatorNotInGame() {
        GameLobby lobby = service.createPublicLobby(4, "SpectatorTest");

        assertThrows(IllegalStateException.class, () ->
            service.removeSpectator(lobby, "NonExistentSpectator")
        );
    }

    @Test
    void testRemovePlayerFromGame() {
        GameLobby lobby = service.createPublicLobby(4, "PlayerTest");

        service.joinLobby(lobby, "Player1");
        service.removePlayer(lobby, "Player1");

        assertFalse(service.findGame(lobby.getGameId()).getPlayers().contains(new Player("Player1")));
    }

    @Test
    void testRemovePlayerNotInGame() {
        GameLobby lobby = service.createPublicLobby(4, "PlayerTest");

        assertThrows(IllegalStateException.class, () ->
            service.removePlayer(lobby, "NonExistentPlayer")
        );
    }

}