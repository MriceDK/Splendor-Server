package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.Player;
import be.howest.ti.game.util.customization.CountryCode;

public class PlayerUnstartedInListView {
    private final Player player;

    public PlayerUnstartedInListView(Player player) {
        this.player = player;
    }

    public String getName() {
        return player.getName();
    }

    public CountryCode getAvatar(){
        return player.getAvatar();
    }

}
