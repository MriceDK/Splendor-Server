package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.Noble;
import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.logic.Token;

import java.util.Map;

public class NobleInListView {

    private final Noble noble;

    public NobleInListView(Noble noble) {
        this.noble = noble;
    }

    public String getName() {
        return noble.name();
    }

    public Map<String, Integer> getNeededBonuses() {
        Map<Token, Integer> bonuses = noble.neededBonuses().getAvailableTokens();
        return Purse.toMapStringInteger(bonuses);
    }

    public int getPrestigePoints() {
        return noble.prestigePoints();
    }

}
