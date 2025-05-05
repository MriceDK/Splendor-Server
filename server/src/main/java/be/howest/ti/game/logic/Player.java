package be.howest.ti.game.logic;

import java.util.*;

public class Player {

    private final String name;
    private Purse tokens; // TODO make final
    private Purse bonuses; // TODO make final
    private Set<Noble> acquiredNobles; // TODO make final
    private int prestigePoints;
    private final List<Development> reservedCards;

    public Player (String name){
        this.name = name;
        this.reservedCards = new ArrayList<>();
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

    public List<Development> getReservedCards() {
        return reservedCards;
    }

    public void buyDevelopment(Development development, Purse payment){
        prestigePoints += development.prestigePoints();
        bonuses.addToken(development.bonus(), 1);
        tokens.removeTokens(payment.getTokens());
    }

    public void reserveDevelopment(Development development){
        if (reservedCards.size() == 3) {
            throw new IllegalStateException("You can only have 3 reserved cards at a time");
        }
        reservedCards.add(development);
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
