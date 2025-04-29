package be.howest.ti.game.logic;

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
}
