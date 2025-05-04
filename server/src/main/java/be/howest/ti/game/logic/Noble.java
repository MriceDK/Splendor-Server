package be.howest.ti.game.logic;

public class Noble {

    private final String name;
    private final int prestigePoints;
    private final Purse neededBonuses;

    public Noble(String name, int prestigePoints, Purse neededBonuses){
        this.name = name;
        this.prestigePoints = prestigePoints;
        this.neededBonuses = neededBonuses;
    }

    public String getName() {
        return name;
    }

    public int getPrestigePoints() {
        return prestigePoints;
    }

    public Purse getNeededBonuses() {
        return neededBonuses;
    }

    public void nobleVisit(Player player){
        Purse bonusesPlayer = player.getBonuses();
    }
}
