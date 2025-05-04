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

    public void claimNoble(Noble noble){acquiredNobles.add(noble);}

    public void acquireTokens(Map<Token,Integer> tokens){
        //TODO
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

    //For testing purposes
    public void setBonuses(Purse bonuses) {
        this.bonuses = bonuses;
    }

   //For testing purposes
    public void setAcquiredNobles(Set<Noble> nobles) {
        this.acquiredNobles = nobles;
    }
}
