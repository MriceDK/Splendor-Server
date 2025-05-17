package be.howest.ti.game.logic;

import be.howest.ti.game.logic.service.PlayerPrestigePointsOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Development dev1;

    @BeforeEach
    public void init() {
        Purse dev1Cost = new Purse(Map.of(Token.SAPPHIRE, 3, Token.ONYX, 2));
        dev1 = new Development("dev-1", 1, 0, Token.DIAMOND, dev1Cost);
    }

    @Test
    void returnTokensWhenPossible() {
        Player player = new Player("Zelensky");
        Map<Token, Integer> initialTokens = new HashMap<>();
        initialTokens.put(Token.DIAMOND, 5);
        initialTokens.put(Token.RUBY, 4);
        initialTokens.put(Token.EMERALD, 3);
        player.setTokens(new Purse(initialTokens));

        assertEquals(12, player.getTokens().getTotal());

        Map<Token, Integer> tokensToReturn = new HashMap<>();
        tokensToReturn.put(Token.DIAMOND, 1);
        tokensToReturn.put(Token.RUBY, 1);
        Purse returnPurse = new Purse(tokensToReturn);

        player.returnTokens(returnPurse);

        assertEquals(10, player.getTokens().getTotal());

    }

    @Test
    void returnTokensWhenLessThan10Tokens() {
        Player player = new Player("Trump");

        Map<Token, Integer> initialTokens = new HashMap<>();
        initialTokens.put(Token.ONYX, 1);
        initialTokens.put(Token.SAPPHIRE, 2);
        initialTokens.put(Token.RUBY, 3);
        player.setTokens(new Purse(initialTokens));

        assertEquals(6, player.getTokens().getTotal());

        Purse tokensToReturn = new Purse(Map.of(Token.ONYX, 1, Token.RUBY, 1));

        // This should throw because player only has 6 tokens (less than 10)
        assertThrows(IllegalStateException.class, () -> player.returnTokens(tokensToReturn));
    }

    @Test
    void returnTokensWhenStillMoreThan10Tokens() {
        Player player = new Player("Bart De Wever");
        Map<Token, Integer> initialTokens = new HashMap<>();
        initialTokens.put(Token.ONYX, 4);
        initialTokens.put(Token.SAPPHIRE, 2);
        initialTokens.put(Token.DIAMOND, 2);
        initialTokens.put(Token.EMERALD, 3);
        initialTokens.put(Token.RUBY, 3);
        player.setTokens(new Purse(initialTokens));

        assertEquals(14, player.getTokens().getTotal());

        Purse  tokensToReturn = new Purse(Map.of(Token.ONYX, 2));

        assertThrows(IllegalArgumentException.class, () -> player.returnTokens(tokensToReturn));
    }

    @Test
    void returnTokensWhenResultIsLessThen0() {
        Player player = new Player("Macron");
        Map<Token, Integer> initialTokens = new HashMap<>();
        initialTokens.put(Token.DIAMOND, 3);
        initialTokens.put(Token.RUBY, 2);
        initialTokens.put(Token.EMERALD, 4);
        initialTokens.put(Token.ONYX, 3);
        player.setTokens(new Purse(initialTokens));

        assertEquals(12, player.getTokens().getTotal());

        Purse tokensToReturn = new Purse(Map.of(Token.RUBY, 4));

        assertThrows(IllegalArgumentException.class, () -> player.returnTokens(tokensToReturn));
    }

    @Test
    void returnTokensUntilMaxTenTokens() {
        Player player = new Player("Mark Rutte");
        Map<Token, Integer> initialTokens = new HashMap<>();
        initialTokens.put(Token.DIAMOND, 3);
        initialTokens.put(Token.RUBY, 2);
        initialTokens.put(Token.EMERALD, 4);
        initialTokens.put(Token.ONYX, 3);
        player.setTokens(new Purse(initialTokens));

        assertEquals(12, player.getTokens().getTotal());

        Purse tokensToReturn = new Purse(Map.of(Token.RUBY, 1, Token.EMERALD, 3));

        assertThrows(IllegalArgumentException.class, () -> player.returnTokens(tokensToReturn));

    }

    @Test
    public void buyDevelopmentCardWithGoldToken() {
        Player player = new Player("Alice");
        player.setTokens(new Purse(Map.of(
                Token.GOLD, 4
        )));

        Development development = new Development("text", 1, 2, Token.DIAMOND, new Purse(Map.of(
                Token.SAPPHIRE, 2,
                Token.ONYX, 1
        )));

        player.buyDevelopment(development, new Purse(Map.of(
                Token.GOLD, 3
        )));

        assertEquals(List.of(development),player.getOwnedDevelopments());
        assertEquals(new Purse(Map.of(
                Token.GOLD, 1
        )), player.getTokens());
    }

    @Test
    public void buyDevelopmentCardWithTooLessGoldToken() {
        Player player = new Player("Alice");
        player.setTokens(new Purse(Map.of(
                Token.GOLD, 1
        )));

        Development development = new Development("text", 1, 2, Token.DIAMOND, new Purse(Map.of(
                Token.SAPPHIRE, 2,
                Token.ONYX, 1
        )));

        assertThrows(IllegalArgumentException.class, () -> player.buyDevelopment(development, new Purse(Map.of(
                Token.GOLD, 3
        ))));
        assertTrue(player.getOwnedDevelopments().isEmpty());
        assertEquals(new Purse(Map.of(
                Token.GOLD, 1
        )), player.getTokens());
    }

    @Test
    public void buyingDevelopmentIsPossibleWhenYouHaveMoreBonusesThanCostOfDevelopment() {
        Player player = new Player("Alice");
        player.setBonuses( new Purse(Map.of(Token.SAPPHIRE, 4, Token.ONYX, 5)) );

        player.buyDevelopment(dev1, new Purse());

        assertEquals(List.of(dev1), player.getOwnedDevelopments());
    }

    @Test
    public void testPlayerOrderByDescendingPrestigePoints() {
        Player p1 = new Player("Joel");
        Player p2 = new Player("Ellie");
        Player p3 = new Player("Abby");
        Player p4 = new Player("Tommy");

        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        p1.buyDevelopment(new Development("test", 1, 5, Token.EMERALD, new Purse()), new Purse());
        p1.buyDevelopment(new Development("test", 1, 5, Token.EMERALD, new Purse()), new Purse());

        p2.buyDevelopment(new Development("test", 1, 2, Token.EMERALD, new Purse()), new Purse());
        p2.buyDevelopment(new Development("test", 1, 2, Token.EMERALD, new Purse()), new Purse());
        p2.buyDevelopment(new Development("test", 1, 2, Token.EMERALD, new Purse()), new Purse());

        p3.buyDevelopment(new Development("test", 1, 6, Token.EMERALD, new Purse()), new Purse());
        p3.buyDevelopment(new Development("test", 1, 3, Token.EMERALD, new Purse()), new Purse());
        p3.buyDevelopment(new Development("test", 1, 1, Token.EMERALD, new Purse()), new Purse());
        p3.buyDevelopment(new Development("test", 1, 2, Token.EMERALD, new Purse()), new Purse());

        p4.buyDevelopment(new Development("test", 1, 6, Token.EMERALD, new Purse()), new Purse());
        p4.buyDevelopment(new Development("test", 1, 6, Token.EMERALD, new Purse()), new Purse());


        System.out.println(players);
        players.sort(new PlayerPrestigePointsOrder());
        System.out.println(players);

        assertEquals(p4, players.getFirst());
        assertEquals(p3, players.get(1));
        assertEquals(p1, players.get(2));
        assertEquals(p2, players.getLast());

    }

}