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
        GameSuperclass game = new SplendorGame(lobby);

        assertEquals(1, game.getGameId());
        assertNull(game.getGameName());
        assertEquals(0, game.getTotalPlayers());
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

        SplendorGame game = new SplendorGame(lobby);
        game.setCurrentPlayer(player);
        game.setUnclaimedNobles(Set.of(noble));


        game.checkForNoble();
        assertTrue(player.getAcquiredNobles().contains(noble));
    }

    @Test
    void checkForNobleBad() {
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