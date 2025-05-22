package be.howest.ti.game.web.views.request.operations;

import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.logic.Token;
import be.howest.ti.game.web.views.request.BaseSplendorRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.Map;

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
        Map<Token, Integer> mapToAdd = new HashMap<>();
        JsonObject jsonObjectToIterate = params.body().getJsonObject().getJsonObject("neededBonuses");

        getPayment(jsonObjectToIterate);

        return new Purse(mapToAdd);
    }

}
