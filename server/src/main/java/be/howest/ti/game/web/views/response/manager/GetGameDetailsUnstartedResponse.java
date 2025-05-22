package be.howest.ti.game.web.views.response.manager;

import be.howest.ti.game.logic.GameSuperclass;
import be.howest.ti.game.logic.Player;

import java.util.ArrayList;
import java.util.List;

public class GetGameDetailsUnstartedResponse extends GetGameDetailsResponse {

    public GetGameDetailsUnstartedResponse(GameSuperclass game) {
        super(game);
    }

    public List<String> getPlayers() {
        List<String> res = new ArrayList<>();

        for (Player player : unstartedGame().getPlayers()) {

            res.add(player.getName());

        }

        return res;
    }
}
