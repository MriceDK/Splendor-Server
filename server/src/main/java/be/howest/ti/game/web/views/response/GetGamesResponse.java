package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.GameSuperclass;
import be.howest.ti.game.web.views.GameInListView;
import be.howest.ti.game.web.views.request.GetGamesRequest;

import java.util.ArrayList;
import java.util.List;

public class GetGamesResponse extends AbstractResponseWithHiddenStatus {

    private final List<GameSuperclass> games;

    public GetGamesResponse(List<GameSuperclass> games) {
        super(200);
        this.games = games;
    }

    public List<GameInListView> getGames() {
        List<GameInListView> res = new ArrayList<>();

        for (GameSuperclass game : games) {
            res.add(new GameInListView(game));
        }

        return res;
    }

}
