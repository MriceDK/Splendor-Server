package be.howest.ti.game.web.views.request.manager;

import be.howest.ti.game.web.views.request.BaseSplendorRequest;
import io.vertx.ext.web.RoutingContext;

public class CreateGameRequest extends BaseSplendorRequest {

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

    public String getPassword() {
        try {
            String password = params.body().getJsonObject().getString("password");
            return (password == null || password.isEmpty()) ? null : password;
        } catch (NullPointerException ex) {
            return null;
        }
    }
}