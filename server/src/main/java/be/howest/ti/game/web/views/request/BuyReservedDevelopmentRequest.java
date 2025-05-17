package be.howest.ti.game.web.views.request;

import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.logic.Token;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.Map;

public class BuyReservedDevelopmentRequest extends BaseSplendorRequest {
    public BuyReservedDevelopmentRequest(RoutingContext ctx) {
        super(ctx);
    }

    public int getGameId() {
        return params.pathParameter("gameId").getInteger();
    }

    public String getPlayerName() {
        return params.pathParameter("playerName").getString();
    }

    public String getDevelopmentName(){
        return params.pathParameter("development").getString();
    }

    public Purse getPayment(){
        Map<Token, Integer> mapToAdd = new HashMap<>();
        JsonObject jsonObjectToIterate = params.body().getJsonObject().getJsonObject("payment");

        for (Map.Entry<String, Object> paymentToken : jsonObjectToIterate){

            Token tokenToAdd = Token.valueOf(paymentToken.getKey().toUpperCase());
            int valueToAdd = (Integer) paymentToken.getValue();

            mapToAdd.put(tokenToAdd, valueToAdd);

        }
        return new Purse(mapToAdd);
    }
}
