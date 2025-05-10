package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Purse;

public class UpdateTokensResponse extends AbstractResponseWithHiddenStatus  {
    private final Purse tokens;

    public UpdateTokensResponse(Purse tokensFromPlayer) {
        super(200);
        this.tokens = tokensFromPlayer;

    }

    public Purse getTokens(){
        return this.tokens;
    }
}
