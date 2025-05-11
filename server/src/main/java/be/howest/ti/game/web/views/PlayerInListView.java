package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.Development;
import be.howest.ti.game.logic.Noble;
import be.howest.ti.game.logic.Player;
import be.howest.ti.game.logic.Token;

import java.util.*;

public class PlayerInListView {

    private final Player player;

    public PlayerInListView(Player player) {
        this.player = player;
    }

    public String getName() {
        return player.getName();
    }

    public Map<Token, Integer> getTokens() {
        return player.getTokens().getAvailableTokens();
    }

    public List<DevelopmentInListView> getReserve() {
        List<DevelopmentInListView> res = new ArrayList<>();

        for (Development reserve : player.getReservedDevelopments()) {
            res.add(new DevelopmentInListView(reserve));
        }

        return res;
    }


    public List<DevelopmentInListView> getBuilt() {
        List<DevelopmentInListView> res = new ArrayList<>();

       for (Development development : player.getOwnedDevelopments()) {
          res.add(new DevelopmentInListView(development));
       }

        return res;
    }

    public Set<NobleInSetView> getNobles() {
        Set<NobleInSetView> res = new HashSet<>();

        for (Noble acquiredNoble : player.getAcquiredNobles()) {

            res.add(new NobleInSetView(acquiredNoble));

        }

        return res;
    }

    public int getTotalPrestigePoints() {
        return player.getPrestigePoints();
    }

    public Map<Token, Integer> getBonuses() {
        return player.getBonuses().getAvailableTokens();
    }

}
