package be.howest.ti.game.logic;

import be.howest.ti.game.logic.exceptions.SplendorGameRuleException;

import java.util.*;

public class Purse {

    private final Map<Token, Integer> tokens;

    public Purse() {
        this.tokens = new HashMap<>();
        for (Token token : Token.values()) {
            this.tokens.put(token, 0);
        }
    }

    public Purse(Map<Token, Integer> tokens) {
        this.tokens = new HashMap<>(tokens);
        for (Token token : Token.values()) {
            this.tokens.putIfAbsent(token, 0);
        }
    }

    public Map<Token, Integer> getTokens() {
        return tokens;
    }

    public int getTokenValue(Token token) {
        return tokens.get(token);
    }

    public Map<Token, Integer> getNormalTokens() {
        Map<Token, Integer> res = new HashMap<>();

        tokens.forEach((token, value) -> {
            if (!token.equals(Token.GOLD)) {
                res.put(token, value);
            }
        });

        return res;
    }

    public int getTotal() {
        int total = 0;
        for (int value : this.tokens.values()) {
            total += value;
        }
        return total;
    }

    private void checkIfDeleteIsAllowed(Token tokenName, int amount) {
        if (this.tokens.get(tokenName) - amount < 0) {
            throw new SplendorGameRuleException("You can't delete more tokens than there are of this type");
        }
        if (amount < 0) {
            throw new SplendorGameRuleException("You can only delete a positive amount of a token");
        }
    }

    private void checkIfAddIsAllowed(int amount) {
        if (amount < 0) {
            throw new SplendorGameRuleException("You can only add a positive amount of a token");
        }
    }

    public void addToken(Token tokenToAdd, Integer amount) {
        checkIfAddIsAllowed(amount);
        this.tokens.put(tokenToAdd, this.tokens.get(tokenToAdd) + amount);
    }

    public void addTokens(Purse tokensToAdd) {
        // Double for loop to first check if all tokens can be added
        for (Token token : tokensToAdd.getTokens().keySet()) {
            checkIfAddIsAllowed(tokensToAdd.getTokens().get(token));
        }
        for (Token token : tokensToAdd.getTokens().keySet()) {
            addToken(token, tokensToAdd.getTokens().get(token));
        }
    }

    public void removeToken(Token tokenToRemove, int amount) {
        checkIfDeleteIsAllowed(tokenToRemove, amount);
        this.tokens.put(tokenToRemove, this.tokens.get(tokenToRemove) - amount);
    }

    public void removeTokens(Purse tokensToRemove) {
        // Double for loop to first check if all tokens can be added
        for (Token token : tokensToRemove.getTokens().keySet()) {
            checkIfDeleteIsAllowed(token, tokensToRemove.getTokens().get(token));
        }
        for (Token token : tokensToRemove.getTokens().keySet()) {
            removeToken(token, tokensToRemove.getTokens().get(token));
        }
    }

    public Map<Token, Integer> getAvailableTokens() {
        Map<Token, Integer> res = new HashMap<>();

        tokens.forEach((token, value) -> {

            if (value > 0) {
                res.put(token, value);
            }

        });

        return res;
    }

    public List<Token> getAvailableTokensList() {
        List<Token> res = new ArrayList<>();

        tokens.forEach((token, value) -> {

            if (value > 0) {
                res.add(token);
            }

        });

        return res;
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
        List<Token> availableTokens = getAvailableTokensList();
        availableTokens.sort(new TokenAlphabeticalOrder());

        List<String> allTokens = new ArrayList<>();

        for (Token token : availableTokens) {
            allTokens.add(getTokenValue(token) + " " + token.toDisplayName());
        }

        return String.join(" | ", allTokens);
    }

    public static Map<String, Integer> toMapStringInteger(Map<Token, Integer> tokens) {
        Map<String, Integer> res = new HashMap<>();

        tokens.forEach((token, value) -> {
            res.put(token.toDisplayName(), value);
        });

        return res;
    }
}
