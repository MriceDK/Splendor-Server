package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.*;
import be.howest.ti.game.util.customization.CountryCode;

import java.util.*;

public class PlayerInListView {

    private final Player player;

    public PlayerInListView(Player player) {
        this.player = player;
    }

    public String getName() {
        return player.getName();
    }

    public CountryCode getAvatar(){
        return player.getAvatar();
    }

    public Map<String, Integer> getTokens() {
        return Purse.toMapStringInteger(player.getTokens().getTokens());
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

    public Map<String, Integer> getBonuses() {
        Map<Token, Integer> bonuses = player.getBonuses().getAvailableTokens();
        return Purse.toMapStringInteger(bonuses) ;
    }

}
