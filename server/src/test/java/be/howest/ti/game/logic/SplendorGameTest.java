package be.howest.ti.game.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SplendorGameTest {

    private GameSuperclass lobby;

    @BeforeEach
    public void init() {
        lobby = new GameLobby(1, 4);
    }

    @Test
    public void copyConstructor() {
        lobby.addPlayer("Bobby");
        SplendorGame game = new SplendorGame(lobby);
        assertEquals(1, game.getGameId());
        assertNull(game.getGameName());
        assertEquals(1, game.getTotalPlayers());
    }

    @Test
    public void reserveDevelopment() {
        lobby.addPlayer("Bobby");
        SplendorGame startedGame = new SplendorGame(lobby);
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), startedGame.findPlayer("Bobby"));
        assertEquals(1,startedGame.getCurrentPlayer().getReservedDevelopments().size());
    }

    @Test
    public void reserveDevelopmentFromLevel() {
        lobby.addPlayer("Bobby");
        SplendorGame startedGame = new SplendorGame(lobby);
        startedGame.reserveDevelopmentFromLevel(1, startedGame.findPlayer("Bobby"));
        assertEquals(1,startedGame.getCurrentPlayer().getReservedDevelopments().size());
    }

    @Test
    public void buyDevelopment() {
        lobby.addPlayer("Bobby");
        SplendorGame startedGame = new SplendorGame(lobby);
        Development firstDevelopment = startedGame.getMarket().getVisibleDevelopments(1).getFirst();
        startedGame.getCurrentPlayer().getTokens().addTokens(firstDevelopment.cost().getTokens());


        Purse payment = firstDevelopment.cost();
        startedGame.buyDevelopment(payment, startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), startedGame.getCurrentPlayer());
        assertEquals(1,startedGame.getCurrentPlayer().getBonuses().getTokens().get(firstDevelopment.bonus()));
    }

    @Test
    void checkForNobleGood() {
        Player player = new Player("Alice");
        lobby.addPlayer("Alice");
        SplendorGame game = new SplendorGame(lobby);

        Set<Noble> nobles = game.getUnclaimedNobles();
        List<Noble> nobleList = new ArrayList<>(nobles);
        Noble wantedNoble = nobleList.getFirst();

        player.setBonuses(wantedNoble.neededBonuses());
        game.setCurrentPlayer(player);

        game.checkForNoble();
        assertTrue(player.getAcquiredNobles().contains(wantedNoble));
    }

    @Test
    void checkForNobleBad() {
        Player player = new Player("Alice");
        lobby.addPlayer("Alice");
        SplendorGame game = new SplendorGame(lobby);

        Set<Noble> nobles = game.getUnclaimedNobles();
        List<Noble> nobleList = new ArrayList<>(nobles);
        Noble wantedNoble = nobleList.getFirst();

        game.setCurrentPlayer(player);

        game.checkForNoble();
        assertFalse(player.getAcquiredNobles().contains(wantedNoble));
    }

    @Test
    void testAcquireValidTokens() {
        lobby.addPlayer("Rutte");
        SplendorGame game = new SplendorGame(lobby);
        Purse requested = new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1, Token.EMERALD, 1));

        Purse tokenBank = new Purse();
        tokenBank.addTokens(Map.of(Token.DIAMOND, 4, Token.SAPPHIRE, 4, Token.EMERALD, 4));

        game.setTokenBank(tokenBank);



        game.acquireTokens(game.getCurrentPlayer(), requested);

        assertEquals(1, game.getCurrentPlayer().getTokens().getTokens().get(Token.DIAMOND));
        assertEquals(3, tokenBank.getTokens().get(Token.DIAMOND));
    }

    @Test
    void testAcquireTokensWrongPlayer() {
        lobby.addPlayer("Musk");
        lobby.addPlayer("Macron");
        SplendorGame game = new SplendorGame(lobby);
        Purse requested = new Purse();
        Purse tokenBank = new Purse();
        tokenBank.addTokens(Map.of(Token.DIAMOND, 4, Token.SAPPHIRE, 4, Token.EMERALD, 4));


        game.setTokenBank(tokenBank);


        requested.addTokens(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1));

        assertThrows(IllegalStateException.class, () -> game.acquireTokens(game.getPlayers().get(1), requested));
    }

    @Test
    void testAcquireInvalidTokenCount() {
        lobby.addPlayer("Vance");
        SplendorGame game = new SplendorGame(lobby);
        Purse requested = new Purse();
        Purse tokenBank = new Purse();
        tokenBank.addTokens(Map.of(Token.DIAMOND, 4, Token.SAPPHIRE, 4, Token.EMERALD, 4));
        game.setTokenBank(tokenBank);

        requested.addTokens(Map.of(Token.DIAMOND, 2, Token.SAPPHIRE, 1));

        assertThrows(IllegalArgumentException.class, () -> game.acquireTokens(game.getCurrentPlayer(), requested));
    }

    @Test
    void testAcquireTooManyTypes() {
        lobby.addPlayer("PM Greenland");
        Purse requested = new Purse();
        SplendorGame game = new SplendorGame(lobby);
        Purse tokenBank = new Purse();
        tokenBank.addTokens(Map.of(Token.DIAMOND, 4, Token.SAPPHIRE, 4, Token.EMERALD, 4));
        game.setTokenBank(tokenBank);
        requested.addTokens(Map.of(
                Token.DIAMOND, 1,
                Token.SAPPHIRE, 1,
                Token.EMERALD, 1,
                Token.RUBY, 1
        ));

        assertThrows(IllegalArgumentException.class, () -> game.acquireTokens(game.getCurrentPlayer(), requested));
    }


}