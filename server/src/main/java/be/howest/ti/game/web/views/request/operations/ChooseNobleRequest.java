package be.howest.ti.game.web.views.request.operations;

import be.howest.ti.game.logic.Purse;
import io.vertx.ext.web.RoutingContext;

public class ChooseNobleRequest extends PaymentReceiver {
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
        return params.body().getJsonObject().getString("name");
    }

    public int getPrestigePoints(){
        return params.body().getJsonObject().getInteger("prestigePoints");
    }

    public Purse getNeededBonuses(){
        return getPayment(params.body().getJsonObject().getJsonObject("neededBonuses"));
    }

}
