package be.howest.ti.game.web.views.request;

import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.logic.Token;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import netscape.javascript.JSObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BuyDevelopmentRequest extends BaseSplendorRequest{

    public BuyDevelopmentRequest(RoutingContext ctx){super (ctx);}

    public int getGameId() {
        return params.pathParameter("gameId").getInteger();
    }

    public String getPlayerName() {
        return params.pathParameter("playerName").getString();
    }

    public String getDevelopmentName(){
        return params.body().getJsonObject().getString("development");
    }

    public Purse getPayment(){
        Map<Token, Integer> mapToAdd = new HashMap<>();
        JsonObject JsonObjectToIterate = params.body().getJsonObject().getJsonObject("payment");

        for (Map.Entry<String, Object> paymentToken : JsonObjectToIterate){

            Token tokenToAdd = Token.valueOf(paymentToken.getKey().toUpperCase());
            int valueToAdd = (Integer) paymentToken.getValue();

            mapToAdd.put(tokenToAdd, valueToAdd);

        }
        return new Purse(mapToAdd);
    }
}
