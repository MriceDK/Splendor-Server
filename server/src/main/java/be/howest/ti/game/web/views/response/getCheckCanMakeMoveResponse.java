package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Player;
import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.web.views.response.operations.UpdateTokensResponse;

public class getCheckCanMakeMoveResponse extends UpdateTokensResponse {

    private final Player currentPlayer;

    public getCheckCanMakeMoveResponse(Player currentPlayer) {
        super(new Purse());
        this.currentPlayer = currentPlayer;
    }

    public String getCurrentPlayer() {
        return currentPlayer.getName();
    }

}
