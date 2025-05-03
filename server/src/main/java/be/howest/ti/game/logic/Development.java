package be.howest.ti.game.logic;

public class Development {

    private String name; // TODO make final
    private int level; // TODO make final
    private int prestigePoints; // TODO make final
    private Token bonus; // TODO make final
    private Purse cost; // TODO make final

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
