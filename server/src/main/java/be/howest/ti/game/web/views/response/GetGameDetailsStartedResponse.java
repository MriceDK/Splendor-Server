package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.GameState;
import be.howest.ti.game.logic.GameSuperclass;
import be.howest.ti.game.logic.Player;
import be.howest.ti.game.logic.SplendorGame;

public class GetGameDetailsStartedResponse extends GetGameDetailsResponse {

    private SplendorGame startedGame;

    public GetGameDetailsStartedResponse(GameSuperclass game) {
        super(game);
        startedGame = (SplendorGame) game;
    }

    // TODO getPlayers -> view van player
    // TODO getMarket -> view van deck
    // TODO getUnclaimedTokens
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
