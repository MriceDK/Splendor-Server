package be.howest.ti.game.logic;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameLobbyTest {

    @Test
    public void creatingALobbyWithoutGameName() {
        GameLobby lobby = new GameLobby(4);

        assertNull(lobby.getGameName());
    }

    @Test
    public void creatingALobbyWithGameName() {
        GameLobby lobby = new GameLobby("Very Creative Game Name", 4);

        assertEquals("Very Creative Game Name", lobby.getGameName());
    }

    @Test
    public void addPlayer() {
        GameLobby lobby = new GameLobby("Very Creative Game Name", 4);

        lobby.addPlayer("John");

        assertEquals(1, lobby.getTotalPlayers());
        assertEquals("John", lobby.getPlayers().getFirst().getName());
    }

    @Test
    public void youCannotHavePlayersWithTheSameName() {
        GameLobby lobby = new GameLobby("Very Creative Game Name", 4);

        lobby.addPlayer("John");
        lobby.addPlayer("Alice");

        assertThrows(IllegalStateException.class, () -> lobby.addPlayer("John"));
        assertEquals(2, lobby.getTotalPlayers());
    }

    @Test
    public void aPlayerCannotJoinWhenMaxSizeIsReached(){
        GameLobby lobby = new GameLobby("Test Game", 2);

        Player player1 = new Player("Alice");
        player1.joinGame(lobby);
        Player player2 = new Player("Marilyn Monroe");
        player2.joinGame(lobby);

        assertThrows(IllegalStateException.class, () -> lobby.addPlayer("Alice"));
        assertEquals(2, lobby.getTotalPlayers());

    }

}