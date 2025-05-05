package be.howest.ti.game.logic;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Player {

    private final String name;
    private Purse tokens; // TODO make final
    private Purse bonuses; // TODO make final
    private Set<Noble> acquiredNobles; // TODO make final
    private int prestigePoints;
    private Development[] reservedCards;

    public Player (String name){
        this.name = name;
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

    public void acquireTokens(Map<Token,Integer> tokensToAcquire){
        int sizeOfTokensToAcquire = tokensToAcquire.size();
        ruleCheckToAcquireTokens(tokensToAcquire, sizeOfTokensToAcquire);
        tokens.addTokens(tokensToAcquire);
    }

    private static void ruleCheckToAcquireTokens(Map<Token, Integer> tokensToAcquire, int sizeOfTokensToAcquire) {
        if (sizeOfTokensToAcquire > 3){
            throw new IllegalArgumentException("You cannot acquire more than three different types of tokens at the same time");
        }

        for (Map.Entry<Token, Integer> tokenToAdd : tokensToAcquire.entrySet()){
            if (tokenToAdd.getValue() > 2){
                throw new IllegalArgumentException("You cannot acquire more than two tokens of the same type");
            }

            if (tokenToAdd.getValue() == 2 && sizeOfTokensToAcquire != 1) {
                throw new IllegalArgumentException("You can only take two of the same token type if you're taking only that type");
            }

        }
    }


    public void returnTokens(Map<Token,Integer> tokens){
        //TODO
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
