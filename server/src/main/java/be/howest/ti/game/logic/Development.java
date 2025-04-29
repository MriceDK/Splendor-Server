package be.howest.ti.game.logic;

public class Development {

    private final String name;
    private final int level;
    private final int prestigePoints;
    private final Token bonus;
    private final Purse cost;

    public Development(String name, int level, int prestigePoints, Token bonus, Purse cost){
        //TODO
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public Token getBonus() {
        return bonus;
    }

    public int getPrestigePoints() {
        return prestigePoints;
    }

    public Purse getCost() {
        return cost;
    }
}
