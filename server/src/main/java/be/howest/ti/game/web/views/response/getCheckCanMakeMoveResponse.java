package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.web.views.response.operations.UpdateTokensResponse;

public class getCheckCanMakeMoveResponse extends UpdateTokensResponse {

    private final boolean canMakeMove;

    public getCheckCanMakeMoveResponse(boolean allowedToMakeMove) {
        super(new Purse());
        this.canMakeMove = allowedToMakeMove;
    }

    public boolean getCanMakeMove() {
        return canMakeMove;
    }

}
