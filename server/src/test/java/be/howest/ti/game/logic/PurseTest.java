package be.howest.ti.game.logic;

import be.howest.ti.game.logic.exceptions.SplendorGameRuleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PurseTest {

    private Purse filledPurse;
    private Purse emptyPurse;


    @BeforeEach
    void setUp() {
        filledPurse = new Purse();
        filledPurse.addToken(Token.DIAMOND, 1);
        filledPurse.addToken(Token.GOLD, 2);
        filledPurse.addToken(Token.EMERALD, 3);
        filledPurse.addToken(Token.ONYX, 4);
        filledPurse.addToken(Token.RUBY, 5);
        filledPurse.addToken(Token.SAPPHIRE, 6);

        emptyPurse = new Purse();
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
        Purse tokensToAdd = new Purse();
        tokensToAdd.addToken(Token.DIAMOND, 3);
        tokensToAdd.addToken(Token.GOLD, 4);

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

        assertThrows(SplendorGameRuleException.class, () -> emptyPurse.removeToken(Token.DIAMOND, 1));
    }

    @Test
    void removeTokens() {
        Purse tokensToRemove = new Purse();
        tokensToRemove.addToken(Token.RUBY, 3);
        tokensToRemove.addToken(Token.SAPPHIRE, 4);

        assertThrows(SplendorGameRuleException.class, () -> emptyPurse.removeTokens(tokensToRemove));

        filledPurse.removeTokens(tokensToRemove);
        assertEquals(2, filledPurse.getTokens().get(Token.RUBY));
        assertEquals(2, filledPurse.getTokens().get(Token.SAPPHIRE));
    }

    @Test
    void testToString() {
        Purse tokens = new Purse(Map.of(Token.ONYX, 2, Token.EMERALD, 3));

        assertEquals("3 Emerald | 2 Onyx", tokens.toString());
    }
}