package be.howest.ti.game.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SplendorGameTest {

    private GameSuperclass lobby;

    @BeforeEach
    public void init() {
        lobby = new GameLobby(1, 4);
    }

    @Test
    public void copyConstructor() {
        GameSuperclass game = new SplendorGame(lobby);

        assertEquals(1, game.getGameId());
        assertNull(game.getGameName());
        assertEquals(0, game.getTotalPlayers());
    }

    @Test
    void testAcquireValidTokens() {

        SplendorGame game = new SplendorGame(lobby);
        Purse requested = new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1, Token.EMERALD, 1));
        Player player1 = new Player("Rutte");
        Purse tokenBank = new Purse();
        game.setCurrentPlayer(player1);
        tokenBank.addTokens(Map.of(Token.DIAMOND, 4, Token.SAPPHIRE, 4, Token.EMERALD, 4));

        game.setTokenBank(tokenBank);



        game.acquireTokens(player1, requested);

        assertEquals(1, player1.getTokens().getTokens().get(Token.DIAMOND));
        assertEquals(3, tokenBank.getTokens().get(Token.DIAMOND));
    }

    @Test
    void testAcquireTokensWrongPlayer() {
        SplendorGame game = new SplendorGame(lobby);
        Player player1 = new Player("Musk");
        Player player2 = new Player("Macron");
        Purse requested = new Purse();
        Purse tokenBank = new Purse();
        game.setCurrentPlayer(player1);

        tokenBank.addTokens(Map.of(Token.DIAMOND, 4, Token.SAPPHIRE, 4, Token.EMERALD, 4));


        game.setTokenBank(tokenBank);


        requested.addTokens(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1));

        assertThrows(IllegalStateException.class, () -> game.acquireTokens(player2, requested));
    }

    @Test
    void testAcquireInvalidTokenCount() {
        SplendorGame game = new SplendorGame(lobby);
        Purse requested = new Purse();
        Purse tokenBank = new Purse();
        Player player1 = new Player("Vance");
        game.setCurrentPlayer(player1);

        tokenBank.addTokens(Map.of(Token.DIAMOND, 4, Token.SAPPHIRE, 4, Token.EMERALD, 4));
        game.setTokenBank(tokenBank);

        requested.addTokens(Map.of(Token.DIAMOND, 2, Token.SAPPHIRE, 1));

        assertThrows(IllegalArgumentException.class, () -> game.acquireTokens(player1, requested));
    }

    @Test
    void testAcquireTooManyTypes() {
        Purse requested = new Purse();
        SplendorGame game = new SplendorGame(lobby);
        Player player1 = new Player("PM Greenland");
        Purse tokenBank = new Purse();
        tokenBank.addTokens(Map.of(Token.DIAMOND, 4, Token.SAPPHIRE, 4, Token.EMERALD, 4));
        game.setTokenBank(tokenBank);

        game.setCurrentPlayer(player1);
        requested.addTokens(Map.of(
                Token.DIAMOND, 1,
                Token.SAPPHIRE, 1,
                Token.EMERALD, 1,
                Token.RUBY, 1
        ));

        assertThrows(IllegalArgumentException.class, () -> game.acquireTokens(player1, requested));
    }


}