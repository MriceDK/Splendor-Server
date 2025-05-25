package be.howest.ti.game.web.views.response.manager;

import be.howest.ti.game.logic.GameSuperclass;
import be.howest.ti.game.logic.Player;
import be.howest.ti.game.web.views.PlayerUnstartedInListView;

import java.util.ArrayList;
import java.util.List;

public class GetGameDetailsUnstartedResponse extends GetGameDetailsResponse {

    public GetGameDetailsUnstartedResponse(GameSuperclass game) {
        super(game);
    }

    public List<PlayerUnstartedInListView> getPlayers() {
        List<PlayerUnstartedInListView> res = new ArrayList<>();

        for (Player player : unstartedGame().getPlayers()) {

            res.add(new PlayerUnstartedInListView(player));

        }

        return res;
    }
}
