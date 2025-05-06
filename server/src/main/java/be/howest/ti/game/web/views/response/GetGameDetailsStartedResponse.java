package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.*;

import java.util.Map;

public class GetGameDetailsStartedResponse extends GetGameDetailsResponse {

    private SplendorGame startedGame;

    public GetGameDetailsStartedResponse(GameSuperclass game) {
        super(game);
        startedGame = (SplendorGame) game;
    }

    // TODO getPlayers -> view van player
    // TODO getMarket -> view van deck

    public Map<Token, Integer> getUnclaimedTokens() {
        return startedGame.getTokenBank().getTokens();
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
