package be.howest.ti.game.web.views.request.manager;

import be.howest.ti.game.util.customization.CountryCode;
import be.howest.ti.game.web.views.request.BaseSplendorRequest;
import io.vertx.ext.web.RoutingContext;

public class CreateGameRequest extends CountryCodeReceiver {

    public CreateGameRequest(RoutingContext ctx) {
        super(ctx);
    }

    public String getGameName() {
        return params.body().getJsonObject().getString("gameName");
    }

    public int getNumberOfPlayers() {
        return params.body().getJsonObject().getInteger("numberOfPlayers");
    }

    public String getPlayerName() {
        return params.body().getJsonObject().getString("playerName");
    }

}