package be.howest.ti.game.logic;

import java.util.HashMap;
import java.util.Map;

public class Purse {

    private final Map<Token, Integer> tokens ;

    public Purse(){
        //TODO
    }

    public Purse(Map<Token, Integer> tokens){

    }

    public Map<Token, Integer> getTokens() {
        return tokens;
    }

    public int getTotal(){
        //TODO
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
