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
        Map<String, Integer> mapTokensToIterate = Purse.toMapStringInteger(tokens.getTokens());
        Map<String, Integer> mapToReturn = new HashMap<>();

        for (Map.Entry<String, Integer> token : mapTokensToIterate.entrySet()){
            if (token.getValue() != NO_VALUE_IN_TOKEN){

                mapToReturn.put(token.getKey(), token.getValue());

            }
        }

        return mapToReturn;
    }
}
