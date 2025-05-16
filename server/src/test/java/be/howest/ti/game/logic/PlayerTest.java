package be.howest.ti.game.logic;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

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

}