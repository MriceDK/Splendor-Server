package be.howest.ti.game.logic;

import java.util.Comparator;

public class PlayerPrestigePointsOrder implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        int differenceInPrestigePoints = -1 * Integer.compare(p1.getPrestigePoints(), p2.getPrestigePoints());

        if (differenceInPrestigePoints == 0 ){
            return Integer.compare(p1.getOwnedDevelopments().size(), p2.getOwnedDevelopments().size());
        } else {
            return differenceInPrestigePoints;
        }
    }
}
