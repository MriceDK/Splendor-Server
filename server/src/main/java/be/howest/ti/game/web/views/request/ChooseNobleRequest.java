package be.howest.ti.game.web.views.request;

import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.logic.Token;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.Map;

public class ChooseNobleRequest extends BaseSplendorRequest{
    public ChooseNobleRequest(RoutingContext ctx) {
        super(ctx);
    }

    public int getGameId() {
        return params.pathParameter("gameId").getInteger();
    }

    public String getPlayerName() {
        return params.pathParameter("playerName").getString();
    }

    public String getNobleName(){
        return params.body().getJsonObject().getJsonObject("noble").getString("name");
    }

    public int getPrestigePoints(){
        return params.body().getJsonObject().getJsonObject("noble").getInteger("prestigePoints");
    }

    public Purse getNeededBonuses(){
        return getTokens();
    }

    public Purse getTokens(){
        Map<Token, Integer> mapToAdd = new HashMap<>();
        JsonObject JsonObjectToIterate = params.body().getJsonObject().getJsonObject("neededBonuses");

        for (Map.Entry<String, Object> developmentBonus : JsonObjectToIterate){
            Token tokenToAdd = Token.valueOf(developmentBonus.getKey().toUpperCase());
            int valueToAdd = (Integer) developmentBonus.getValue();

            mapToAdd.put(tokenToAdd, valueToAdd);


        }
        return new Purse(mapToAdd);
    }

}
