package be.howest.ti.game.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        startedGame.reserveDevelopment("Development 1");
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
        startedGame.getCurrentPlayer().getTokens().addToken(Token.DIAMOND, 1);
        startedGame.getCurrentPlayer().getTokens().addToken(Token.SAPPHIRE, 1);

        Purse payment = new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1));
        startedGame.buyDevelopment(payment, startedGame.getMarket().getVisibleDevelopments(1).getFirst().name());
        assertEquals(0, startedGame.getCurrentPlayer().getTokens().getTokens().get(Token.DIAMOND));
        assertEquals(0, startedGame.getCurrentPlayer().getTokens().getTokens().get(Token.SAPPHIRE));
        assertEquals(1, startedGame.getCurrentPlayer().getBonuses().getTokens().get(Token.EMERALD));
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

}