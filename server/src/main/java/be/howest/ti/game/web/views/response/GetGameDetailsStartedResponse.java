package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.*;
import be.howest.ti.game.web.views.DeckInListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetGameDetailsStartedResponse extends GetGameDetailsResponse {

    private final SplendorGame startedGame;

    public GetGameDetailsStartedResponse(GameSuperclass game) {
        super(game);
        startedGame = (SplendorGame) game;
    }

    // TODO getPlayers -> view van player
    // TODO getMarket -> view van deck

    public List<DeckInListView> getMarket() {
        List<DeckInListView> res = new ArrayList<>();

        for (Deck deck : startedGame.getMarket().getLevels().values()) {
            res.add(new DeckInListView(deck));
        }

        return res;
    }

    public Map<Token, Integer> getUnclaimedTokens() {
        return startedGame.getTokenBank().getTokens();
        // For some reason the tokenNames don't show in the right format as declared in the ToString, get help
    }

    // TODO getUnclaimedNobles -> view van noble

    public GameState getGameState() {
        return startedGame.getGameState();
    }

    public String getCurrentPlayer() {
        return startedGame.getCurrentPlayer().getName();
    }

    public String getWinner() {
        Player winner = startedGame.getWinner();

        if (winner == null) {
            return null;
        } else {
            return winner.getName();
        }
    }


}
