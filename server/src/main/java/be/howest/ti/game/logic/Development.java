package be.howest.ti.game.logic;

public record Development(String name, int level, int prestigePoints, Token bonus, Purse cost) {
    @Override
    public String toString() {
        return "Development{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", prestigePoints=" + prestigePoints +
                ", bonus=" + bonus +
                ", cost=" + cost +
                '}';
    }
}
