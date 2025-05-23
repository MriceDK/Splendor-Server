package be.howest.ti.game.web.views.request.operations;

import be.howest.ti.game.web.views.request.BaseSplendorRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ReserveDevelopmentRequest extends BaseSplendorRequest {

    public ReserveDevelopmentRequest(RoutingContext ctx) {
        super(ctx);
    }

    public int getGameId() {
        return params.pathParameter("gameId").getInteger();
    }

    public String getPlayerName() {
        return params.pathParameter("playerName").getString();
    }

    public String getDevelopmentName() {
        JsonObject developementRequest = params.body().getJsonObject().getJsonObject("development");
        if (developementRequest.containsKey("name")) {
            return developementRequest.getString("name");
        }
        throw new IllegalArgumentException("Please provide a valid development name");
    }

    public int getDevelopmentLevel() {
        JsonObject developementRequest = params.body().getJsonObject().getJsonObject("development");
        if (developementRequest.containsKey("level")) {
            return developementRequest.getInteger("level");
        } else throw new IllegalArgumentException("Please provide a valid level (1-3)");
    }
}
