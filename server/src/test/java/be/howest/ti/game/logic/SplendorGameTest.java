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
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name());
        // TODO: Change this when the actual Development cards are implemented
        // assertEquals uses an exact copy of the Development object that was reserved
        // haven't figured out how to get this any other way
        assertEquals(1,startedGame.getCurrentPlayer().getReservedDevelopments().size());
    }

    @Test
    public void reserveDevelopmentFromLevel() {
        lobby.addPlayer("Bobby");
        SplendorGame startedGame = new SplendorGame(lobby);
        startedGame.reserveDevelopmentFromLevel(1);
        assertEquals(1,startedGame.getCurrentPlayer().getReservedDevelopments().size());
    }

    @Test
    public void buyDevelopment() {
        lobby.addPlayer("Bobby");
        SplendorGame startedGame = new SplendorGame(lobby);
        Development firstDevelopment = startedGame.getMarket().getVisibleDevelopments(1).getFirst();
        startedGame.getCurrentPlayer().getTokens().addTokens(firstDevelopment.cost().getTokens());


        Purse payment = new Purse(firstDevelopment.cost().getTokens());
        startedGame.buyDevelopment(payment, startedGame.getMarket().getVisibleDevelopments(1).getFirst().name());
        assertEquals(1,startedGame.getCurrentPlayer().getBonuses().getTokens().get(firstDevelopment.bonus()));
    }

    @Test
    void checkForNobleGood() {
        Player player = new Player("Alice");

        Map<Token, Integer> playerBonuses = new HashMap<>();
        for (Token token : Token.values()) {
            if (token != Token.GOLD) {
                playerBonuses.put(token, 3);
            }
        }
        Purse bonuses = new Purse(playerBonuses);
        player.setBonuses(bonuses);
        player.setAcquiredNobles(new HashSet<>());

        Map<Token, Integer> nobleRequirements = new HashMap<>();
        for (Token token : Token.values()) {
            if (token != Token.GOLD) {
                nobleRequirements.put(token, 2);
            }
        }
        Noble noble = new Noble("De Wever", 3, new Purse(nobleRequirements));
        lobby.addPlayer("Alice");
        SplendorGame game = new SplendorGame(lobby);
        game.setCurrentPlayer(player);
        game.setUnclaimedNobles(Set.of(noble));


        game.checkForNoble();
        assertTrue(player.getAcquiredNobles().contains(noble));
    }

    @Test
    void checkForNobleBad() {
        Player player = new Player("Alice");
        lobby.addPlayer("Alice");
        Map<Token, Integer> playerBonuses = new HashMap<>();
        for (Token token : Token.values()) {
            if (token != Token.GOLD) {
                playerBonuses.put(token, 3);
            }
        }
        Purse bonuses = new Purse(playerBonuses);
        player.setBonuses(bonuses);
        player.setAcquiredNobles(new HashSet<>());

        Map<Token, Integer> nobleRequirements = new HashMap<>();
        for (Token token : Token.values()) {
            if (token != Token.GOLD) {
                nobleRequirements.put(token, 5);
            }
        }
        Noble noble = new Noble("De Wever", 3, new Purse(nobleRequirements));

        SplendorGame game = new SplendorGame(lobby);
        game.setCurrentPlayer(player);
        game.setUnclaimedNobles(Set.of(noble));

        game.checkForNoble();

        assertFalse(player.getAcquiredNobles().contains(noble));
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