package be.howest.ti.game.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameLobbyTest {

    @Test
    public void creatingALobbyWithoutGameName() {
        GameLobby lobby = new GameLobby(1, 4);

        assertNull(lobby.getGameName());
    }

    @Test
    public void creatingALobbyWithGameName() {
        GameLobby lobby = new GameLobby(1, "Very Creative Game Name", 4);

        assertEquals("Very Creative Game Name", lobby.getGameName());
    }

    @Test
    public void addPlayer() {
        GameLobby lobby = new GameLobby(1, "Very Creative Game Name", 4);

        lobby.addPlayer("John");

        assertEquals(1, lobby.getTotalPlayers());
        assertEquals("John", lobby.getPlayers().getFirst().getName());
    }

    @Test
    public void YouCannotHavePlayersWithTheSameName() {
        GameLobby lobby = new GameLobby(1, "Very Creative Game Name", 4);

        lobby.addPlayer("John");
        lobby.addPlayer("Alice");

        assertThrows(IllegalStateException.class, () -> lobby.addPlayer("John"));
        assertEquals(2, lobby.getTotalPlayers());
    }

    @Test
    public void gameStartsWhenLobbyIsFull() {
        GameLobby lobby = new GameLobby(1, "Very Creative Game Name", 3);

        lobby.addPlayer("John");
        lobby.addPlayer("Alice");
        lobby.addPlayer("Erwin");

        SplendorGame game = lobby.startGame();

        assertEquals(1, game.getGameId());
        assertEquals("Very Creative Game Name", game.getGameName());
        assertEquals(3, game.getMaxPlayers());
        assertEquals(3, game.getTotalPlayers());
    }

    @Test
    public void aPlayerCannotJoinWhenMaxSizeIsReached(){
        GameLobby lobby = new GameLobby(1, "Test Game", 2);

        lobby.addPlayer("John");
        lobby.addPlayer("Alice");



        assertThrows(IllegalStateException.class, () -> lobby.addPlayer("Alice"));
        assertEquals(2, lobby.getTotalPlayers());

    }

}