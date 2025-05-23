package be.howest.ti.game.logic.order.implementations;

import be.howest.ti.game.logic.Token;

import java.util.Comparator;

public class TokenAlphabeticalOrder implements Comparator<Token> {
    @Override
    public int compare(Token t1, Token t2) {
        return t2.compareTo(t1);
    }
}
