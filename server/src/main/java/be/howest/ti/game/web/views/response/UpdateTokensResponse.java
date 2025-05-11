package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.logic.Token;

import java.util.HashMap;
import java.util.Map;

public class UpdateTokensResponse extends AbstractResponseWithHiddenStatus  {
    private final Purse tokens;
    private final static int NO_VALUE_IN_TOKEN = 0;

    public UpdateTokensResponse(Purse tokens){
        super(200);
        this.tokens = tokens;

    }

    public Map<String, Integer> getTokens(){
        Map<String, Integer> mapTokensToReturn = Purse.toMapStringInteger(tokens.getTokens());
        for (Map.Entry<String, Integer> token : mapTokensToReturn.entrySet()){
            if (token.getValue() != NO_VALUE_IN_TOKEN){

                mapTokensToReturn.put(token.getKey(), token.getValue());

            }
        }

        return mapTokensToReturn;
    }
}
