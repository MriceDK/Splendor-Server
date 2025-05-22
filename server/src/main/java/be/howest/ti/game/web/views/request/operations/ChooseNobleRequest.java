package be.howest.ti.game.web.views.request.operations;

import be.howest.ti.game.logic.Purse;
import io.vertx.core.json.JsonObject;
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
        return params.body().getJsonObject().getJsonObject("noble").getString("name");
    }

    public int getPrestigePoints(){
        return params.body().getJsonObject().getJsonObject("noble").getInteger("prestigePoints");
    }

    public Purse getNeededBonuses(){
        Purse purse = new Purse();
        JsonObject jsonObjectToIterate = params.body().getJsonObject().getJsonObject("neededBonuses");

        getPayment(jsonObjectToIterate);

        return purse;
    }

}
