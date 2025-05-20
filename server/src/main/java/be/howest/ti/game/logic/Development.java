package be.howest.ti.game.logic;

public record Development(String name, int level, int prestigePoints, Token bonus, Purse cost) {

    public String toString() {
        return "'" + name() + "' (" + prestigePoints() + " Prestige Points) (" + bonus().toDisplayName() + " Bonus) from level " + level();
    }
}
