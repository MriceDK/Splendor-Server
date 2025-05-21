package be.howest.ti.game.web.views.response.manager;

import be.howest.ti.game.logic.*;
import be.howest.ti.game.util.logger.ActionReport;
import be.howest.ti.game.web.views.ActionReportInListView;
import be.howest.ti.game.web.views.DeckInListView;
import be.howest.ti.game.web.views.NobleInSetView;
import be.howest.ti.game.web.views.PlayerInListView;

import java.util.*;

public class GetGameDetailsStartedResponse extends GetGameDetailsResponse {

    private final SplendorGame startedGame;

    public GetGameDetailsStartedResponse(GameSuperclass game) {
        super(game);
        startedGame = (SplendorGame) game;
    }

    public List<PlayerInListView> getPlayers() {
        List<PlayerInListView> res = new ArrayList<>();

        for (Player player : startedGame.getPlayers()) {

            res.add(new PlayerInListView(player));

        }

        return res;
    }

    public List<DeckInListView> getMarket() {
        List<DeckInListView> res = new ArrayList<>();

        for (Deck deck : startedGame.getMarket().getLevels()) {
            res.add(new DeckInListView(deck));
        }

        return res;
    }

    public Map<String, Integer> getUnclaimedTokens() {
        Map<Token, Integer> tokens = startedGame.getTokenBank().getTokens();
        return Purse.toMapStringInteger(tokens);
    }

    public Set<NobleInSetView> getUnclaimedNobles() {
        Set<NobleInSetView> res = new HashSet<>();

        for (Noble unclaimedNoble : startedGame.getUnclaimedNobles()) {
            res.add(new NobleInSetView(unclaimedNoble));
        }

        return res;
    }

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

    public List<ActionReportInListView> getHistory() {
        List<ActionReportInListView> res = new ArrayList<>();

        for (ActionReport actionReport : startedGame.getHistory().getLogs()) {
            res.add(new ActionReportInListView(actionReport));
        }

        return res;
    }
    public boolean isLastRound() {
        return startedGame.getIsLastRound();
    }


}
