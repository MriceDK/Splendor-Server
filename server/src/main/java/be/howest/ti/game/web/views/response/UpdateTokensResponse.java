package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.logic.Token;

import java.util.HashMap;
import java.util.Map;

public class UpdateTokensResponse extends AbstractResponseWithHiddenStatus  {
    private final Purse tokens;

    public UpdateTokensResponse(Purse tokens){
        super(200);
        this.tokens = tokens;

    }

    public Map<String, Integer> getTokens(){

        return Purse.toMapStringInteger(tokens.getAvailableTokens());

    }
}
