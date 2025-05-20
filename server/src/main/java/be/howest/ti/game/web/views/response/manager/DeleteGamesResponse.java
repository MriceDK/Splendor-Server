package be.howest.ti.game.web.views.response.manager;

import be.howest.ti.game.logic.GameSuperclass;
import be.howest.ti.game.web.views.GameInListView;
import be.howest.ti.game.web.views.response.AbstractResponseWithHiddenStatus;

import java.util.ArrayList;
import java.util.List;

public class DeleteGamesResponse extends AbstractResponseWithHiddenStatus {

    private final List<GameSuperclass> gamesThatWereDeleted;

    public DeleteGamesResponse(List<GameSuperclass> gamesThatWereDeleted) {
        super(200);
        this.gamesThatWereDeleted = gamesThatWereDeleted;
    }

    public List<GameInListView> getGames() {
        List<GameInListView> gamesInListView = new ArrayList<>();
        for (GameSuperclass game : gamesThatWereDeleted) {
            GameInListView convertedGame = new GameInListView(game);
            gamesInListView.add(convertedGame);
        }
        return gamesInListView;
    }
}
