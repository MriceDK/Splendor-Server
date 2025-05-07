package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.Noble;
import be.howest.ti.game.logic.Token;

import java.util.Map;

public class NobleInSetView {

    private final Noble noble;

    public NobleInSetView(Noble noble) {
        this.noble = noble;
    }

    public String getName() {
        return noble.name();
    }

    public Map<Token, Integer> getNeededBonusses() {
        return noble.neededBonuses().getAvailableTokens();
    }

    public int getPrestigePoints() {
        return noble.prestigePoints();
    }

}
