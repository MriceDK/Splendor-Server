package be.howest.ti.game.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Purse {

    private final Map<Token, Integer> tokens ;

    public Purse(){
        this.tokens = new HashMap<>();
        for (Token token : Token.values()) {
            this.tokens.put(token, 0);
        }
    }

    public Purse(Map<Token, Integer> tokens){
        this.tokens = new HashMap<>(tokens);
        for (Token token : Token.values()) {
            this.tokens.putIfAbsent(token, 0);
        }
    }

    public Map<Token, Integer> getTokens() {
        return tokens;
    }

    public int getTotal(){
        int total = 0;
        for (Integer value : this.tokens.values()) {
            total += value;
        }
        return total;
    }

    private void checkIfDeleteIsAllowed(Token tokenName, int amount) {
        if (this.tokens.get(tokenName) - amount < 0) {
            throw new IllegalArgumentException("You can't delete more tokens than there are of this type");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("You can only delete a positive amount of a token");
        }
    }

    private void checkIfAddIsAllowed(Token tokenName, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("You can only add a positive amount of a token");
        }
    }

    public void addToken(Token tokenToAdd, Integer amount){
        checkIfAddIsAllowed(tokenToAdd, amount);
        this.tokens.put(tokenToAdd, this.tokens.get(tokenToAdd) + amount);
    }

    public void addTokens(Map<Token, Integer> tokensToAdd){
        // Double for loop to first check if all tokens can be added
        for (Token token : tokensToAdd.keySet()) {
            checkIfAddIsAllowed(token, tokensToAdd.get(token));
        }
        for (Token token : tokensToAdd.keySet()) {
            addToken(token, tokensToAdd.get(token));
        }
    }

    public void removeToken(Token tokenToRemove, int amount){
        checkIfDeleteIsAllowed(tokenToRemove, amount);
        this.tokens.put(tokenToRemove, this.tokens.get(tokenToRemove) - amount);
    }

    public void removeTokens(Map<Token, Integer> tokensToRemove){
        // Double for loop to first check if all tokens can be added
        for (Token token : tokensToRemove.keySet()) {
            checkIfDeleteIsAllowed(token, tokensToRemove.get(token));
        }
        for (Token token : tokensToRemove.keySet()) {
            removeToken(token, tokensToRemove.get(token));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Purse purse = (Purse) o;
        return Objects.equals(tokens, purse.tokens);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tokens);
    }

    @Override
    public String toString() {
        return tokens.toString();

    }
}
