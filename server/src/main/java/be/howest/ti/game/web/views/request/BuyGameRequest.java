package be.howest.ti.game.web.views.request;

import io.vertx.ext.web.RoutingContext;

public class BuyGameRequest extends BaseSplendorRequest{

    public BuyGameRequest(RoutingContext ctx){super (ctx);}

    public int getGameId() {
        return params.pathParameter("gameId").getInteger();
    }

    public String getPlayerName() {
        return params.pathParameter("playerName").getString();
    }
}
