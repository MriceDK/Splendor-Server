package be.howest.ti.game.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PurseTest {

    private Map<Token, Integer> emptyTokenMap;
    private Map<Token, Integer> startTokens;
    private Purse filledPurse;
    private Purse emptyPurse;


    @BeforeEach
    public void setUp() {

        emptyTokenMap = new HashMap<>();
        emptyTokenMap.put(Token.DIAMOND, 0);
        emptyTokenMap.put(Token.GOLD, 0);
        emptyTokenMap.put(Token.EMERALD, 0);
        emptyTokenMap.put(Token.ONYX, 0);
        emptyTokenMap.put(Token.RUBY, 0);
        emptyTokenMap.put(Token.SAPPHIRE, 0);


        startTokens = new HashMap<>();
        startTokens.put(Token.DIAMOND, 1);
        startTokens.put(Token.GOLD, 2);
        startTokens.put(Token.EMERALD, 3);
        startTokens.put(Token.ONYX, 4);
        startTokens.put(Token.RUBY, 5);
        startTokens.put(Token.SAPPHIRE, 6);


        emptyPurse = new Purse();
        filledPurse = new Purse(startTokens);
    }

    @Test
    public void getTokens() {
        assertEquals(emptyTokenMap, emptyPurse.getTokens());
        assertEquals(startTokens, filledPurse.getTokens());
    }

    @Test
    void getTotal() {
        assertEquals(0, emptyPurse.getTotal());
        assertEquals(21, filledPurse.getTotal());
    }

    @Test
    void addToken() {
        emptyPurse.addToken(Token.DIAMOND,1);
        assertEquals(1, emptyPurse.getTokens().get(Token.DIAMOND));
        emptyPurse.addToken(Token.GOLD,2);
        assertEquals(2, emptyPurse.getTokens().get(Token.GOLD));
    }

    @Test
    void addTokens() {
        Map<Token, Integer> tokensToAdd = new HashMap<>();
        tokensToAdd.put(Token.DIAMOND, 3);
        tokensToAdd.put(Token.GOLD, 4);

        emptyPurse.addTokens(tokensToAdd);
        assertEquals(3, emptyPurse.getTokens().get(Token.DIAMOND));
        assertEquals(4, emptyPurse.getTokens().get(Token.GOLD));
        assertEquals(0, emptyPurse.getTokens().get(Token.EMERALD));
        assertEquals(0, emptyPurse.getTokens().get(Token.ONYX));
        assertEquals(0, emptyPurse.getTokens().get(Token.RUBY));
        assertEquals(0, emptyPurse.getTokens().get(Token.SAPPHIRE));
    }

    @Test
    void removeToken() {
        filledPurse.removeToken(Token.DIAMOND, 1);
        assertEquals(0, filledPurse.getTokens().get(Token.DIAMOND));

        filledPurse.removeToken(Token.GOLD, 2);
        assertEquals(0, filledPurse.getTokens().get(Token.GOLD));

        assertThrows(IllegalArgumentException.class, () -> emptyPurse.removeToken(Token.DIAMOND, 1));
    }

    @Test
    void removeTokens() {
        System.out.println(filledPurse);
        Map<Token, Integer> tokensToRemove = new HashMap<>();
        tokensToRemove.put(Token.RUBY, 3);
        tokensToRemove.put(Token.SAPPHIRE, 4);

        assertThrows(IllegalArgumentException.class, () -> emptyPurse.removeTokens(tokensToRemove));

        filledPurse.removeTokens(tokensToRemove);
        assertEquals(2, filledPurse.getTokens().get(Token.RUBY));
        assertEquals(2, filledPurse.getTokens().get(Token.SAPPHIRE));
    }
}