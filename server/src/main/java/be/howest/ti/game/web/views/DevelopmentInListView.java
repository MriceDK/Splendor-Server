package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.Development;
import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.logic.Token;

import java.util.Map;

public class DevelopmentInListView {

    private final Development development;

    public DevelopmentInListView(Development development) {
        this.development = development;
    }

    public String getName() {
        return development.name();
    }


    public int getLevel() {
        return development.level();
    }

    public Map<String, Integer> getCost() {
        Map<Token, Integer> cost = development.cost().getAvailableTokens();
        return Purse.toMapStringInteger(cost);
    }

    public String getBonus() {
        return development.bonus().toString();
    }

    public int getPrestigePoints() {
        return development.prestigePoints();
    }

}
