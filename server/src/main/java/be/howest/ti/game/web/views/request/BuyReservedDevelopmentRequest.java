package be.howest.ti.game.web.views.request;

import io.vertx.ext.web.RoutingContext;

public class BuyReservedDevelopmentRequest extends PaymentReceiver {
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

}
