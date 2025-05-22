package be.howest.ti.game.web.views.request.operations;

import be.howest.ti.game.logic.Purse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class BuyDevelopmentRequest extends PaymentReceiver{

    public BuyDevelopmentRequest(RoutingContext ctx) {
        super(ctx);
    }

    public int getGameId() {
        return params.pathParameter("gameId").getInteger();
    }

    public String getPlayerName() {
        return params.pathParameter("playerName").getString();
    }

    public String getDevelopmentName() {
        return params.body().getJsonObject().getJsonObject("development").getString("name");
    }

    public JsonObject getPaymentObject() {
        return params.body().getJsonObject().getJsonObject("payment");
    }

}
