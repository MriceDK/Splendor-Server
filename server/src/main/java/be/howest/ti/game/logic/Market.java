package be.howest.ti.game.logic;
import be.howest.ti.game.logic.exceptions.SplendorGameResourceNotFoundException;
import be.howest.ti.game.util.reader.DevelopmentReader;

import java.util.*;

public class Market {

    private final Deck[] levels = new Deck[3];

    public Market() {
        DevelopmentReader reader = new DevelopmentReader();
        levels[0] = new Deck(reader.getFirstLevel(), 1);
        levels[1] = new Deck(reader.getSecondLevel(), 2);
        levels[2] = new Deck(reader.getThirdLevel(), 3);
    }

    public Deck[] getLevels() {
        return levels;
    }

    public List<Development> getVisibleDevelopments(int level) {
        return levels[level - 1].getVisibleDevelopments();
    }

    public int getTotalInvisibleDevelopments(int level) {
        return levels[level - 1].getTotalInvisible();
    }

    public Development takeTopDevelopment(int level) {
        return levels[level - 1].takeTopDevelopment();
    }

    public Development removeVisibleDevelopment(Development matchingDevelopment) {
        return levels[matchingDevelopment.level() - 1].removeVisibleDevelopment(matchingDevelopment);
    }

    public void refillMarket(int level){
        levels[level - 1].refillMarket();
    }

    public Development findMatchingDevelopmentOverAllLevels(String developmentName) {
        Development matchingDevelopment = null;
        for (Deck level : levels) {
            if (level.getVisibleDevelopments().contains(level.findMatchingDevelopment(developmentName)) && matchingDevelopment == null) {
                matchingDevelopment = level.findMatchingDevelopment(developmentName);
            }
        }
        if (matchingDevelopment == null) {
            throw new SplendorGameResourceNotFoundException("Development not found in visible developments");
        }
        return matchingDevelopment;
    }
}
