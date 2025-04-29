package be.howest.ti.game.logic;

import java.util.Map;

public class Player {

    private final String name;
    private final Purse tokens;
    private final Purse bonuses;
    private final Set<Noble> acquiredNobles;
    private int prestigePoints;
    private Development[] reservedCards;

    public Player (String name){
        //TODO

    }

    public String getName() {
        return name;
    }

    public Purse getTokens() {
        return tokens;
    }

    public Purse getBonuses() {
        return bonuses;
    }

    public Set<Noble> getAcquiredNobles() {
        return acquiredNobles;
    }

    public int getPrestigePoints() {
        return prestigePoints;
    }

    public Development[] getReservedCards() {
        return reservedCards;
    }

    public void buyDevelopment(Development development){
        //TODO
    }

    public void reserveDevelopment(Development development){
        //TODO
    }

    public void claimNoble(Noble noble){
        //TODO
    }

    public void acquireTokens(Map<Token,Integer> tokens){
        //TODO
    }

    public void returnTokens(Map<Token,Integer> tokens){
        //TODO
    }

}
