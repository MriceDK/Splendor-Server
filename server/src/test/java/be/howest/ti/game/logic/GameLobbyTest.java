package be.howest.ti.game.logic;

import be.howest.ti.game.logic.exceptions.SplendorGameRuleException;
import be.howest.ti.game.util.customization.CountryCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameLobbyTest {

    @Test
    void creatingALobbyWithoutGameName() {
        GameLobby lobby = new GameLobby(1, 4);

        assertNull(lobby.getGameName());
    }

    @Test
    void creatingALobbyWithGameName() {
        GameLobby lobby = new GameLobby(1, "Very Creative Game Name", 4);

        assertEquals("Very Creative Game Name", lobby.getGameName());
    }

    @Test
    void addPlayer() {
        GameLobby lobby = new GameLobby(1, "Very Creative Game Name", 4);

        lobby.addPlayer("John", CountryCode.UN);

        assertEquals(1, lobby.getTotalPlayers());
        assertEquals("John", lobby.getPlayers().getFirst().getName());
    }

    @Test
    void YouCannotHavePlayersWithTheSameName() {
        GameLobby lobby = new GameLobby(1, "Very Creative Game Name", 4);

        lobby.addPlayer("John", CountryCode.UN);
        lobby.addPlayer("Alice", CountryCode.UN);

        assertThrows(IllegalStateException.class, () -> lobby.addPlayer("John", CountryCode.UN));
        assertEquals(2, lobby.getTotalPlayers());
    }

    @Test
    void aPlayerCannotJoinWhenMaxSizeIsReached(){
        GameLobby lobby = new GameLobby(1, "Test Game", 2);

        lobby.addPlayer("John",CountryCode.UN);
        lobby.addPlayer("Alice", CountryCode.UN);



        assertThrows(SplendorGameRuleException.class, () -> lobby.addPlayer("Alice", CountryCode.UN));
        assertEquals(2, lobby.getTotalPlayers());

    }

}