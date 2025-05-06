package be.howest.ti.game.logic;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void returnTokensWhenPossible() {
        Player player = new Player("Alice");
        Map<Token, Integer> initialTokens = new HashMap<>();
        initialTokens.put(Token.DIAMOND, 5);
        initialTokens.put(Token.RUBY, 4);
        initialTokens.put(Token.EMERALD, 3);
        player.setTokens(new Purse(initialTokens));

        assertEquals(12, player.getTokens().getTotal());

        Map<Token, Integer> tokensToReturn = new HashMap<>();
        tokensToReturn.put(Token.DIAMOND, 3);
        tokensToReturn.put(Token.RUBY, 1);
        Purse returnPurse = new Purse(tokensToReturn);

        player.returnTokens(returnPurse.getTokens());

        assertEquals(8, player.getTokens().getTotal());

    }

    @Test
    void returnTokensWhenLessThan10Tokens() {
        Player player = new Player("Bob");
        Map<Token, Integer> initialTokens = new HashMap<>();
        initialTokens.put(Token.ONYX, 1);
        initialTokens.put(Token.SAPPHIRE, 2);
        initialTokens.put(Token.RUBY, 3);
        player.setTokens(new Purse(initialTokens));

        assertEquals(6, player.getTokens().getTotal());

        Map<Token, Integer> tokensToReturn = new HashMap<>();
        tokensToReturn.put(Token.ONYX, 1);
        tokensToReturn.put(Token.RUBY, 1);
        Purse returnPurse = new Purse(tokensToReturn);

        assertThrows(IllegalStateException.class, () -> player.returnTokens(returnPurse.getTokens()));
    }

    @Test
    void returnTokensWhenStillMoreThan10Tokens() {
        Player player = new Player("Bob");
        Map<Token, Integer> initialTokens = new HashMap<>();
        initialTokens.put(Token.ONYX, 4);
        initialTokens.put(Token.SAPPHIRE, 2);
        initialTokens.put(Token.DIAMOND, 2);
        initialTokens.put(Token.EMERALD, 3);
        initialTokens.put(Token.RUBY, 3);
        player.setTokens(new Purse(initialTokens));

        assertEquals(14, player.getTokens().getTotal());

        Map<Token, Integer> tokensToReturn = new HashMap<>();
        tokensToReturn.put(Token.ONYX, 2);
        Purse returnPurse = new Purse(tokensToReturn);

        assertThrows(IllegalArgumentException.class, () -> player.returnTokens(tokensToReturn));
    }
}