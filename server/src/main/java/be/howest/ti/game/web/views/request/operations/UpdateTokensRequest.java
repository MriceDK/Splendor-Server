package be.howest.ti.game.web.views.request.operations;

import be.howest.ti.game.logic.Purse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UpdateTokensRequest extends PaymentReceiver {
    public UpdateTokensRequest(RoutingContext ctx) {
        super(ctx);
    }

    public int getGameId() {
        return params.pathParameter("gameId").getInteger();
    }

    public String getPlayerName() {
        return params.pathParameter("playerName").getString();
    }

    public Purse getTokensToAdd() {
        return getPayment(getAddOrReturn());
    }

    private JsonObject getAddOrReturn() {
        JsonObject json = params.body().getJsonObject();
        return json.getJsonObject("return", json.getJsonObject("take"));
    }

    public boolean addOrReturnCheck() {
        JsonObject json = params.body().getJsonObject();
        if (json.containsKey("take")) {
            return true;
        } else if (json.containsKey("return")) {
            return false;
        } else {
            throw new IllegalStateException("A bad JSON Object was provided");
        }
    }
}
