package be.howest.ti.game.logic;

import java.util.HashMap;
import java.util.Map;

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

    public void addToken(Token tokenToAdd, int amount){
        //TODO
    }

    public void addTokens(Map<Token, Integer> tokensToAdd){
        //TODO
    }

    public void removeToken(Token tokenToRemove, int amount){
        //TODO
    }

    public void removeTokens(Map<Token, Integer> tokensToRemove){
        //TODO
    }


}
