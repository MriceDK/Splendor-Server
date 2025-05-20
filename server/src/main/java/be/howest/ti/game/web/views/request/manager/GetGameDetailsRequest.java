package be.howest.ti.game.web.views.request.manager;

import be.howest.ti.game.web.views.request.BaseSplendorRequest;
import io.vertx.ext.web.RoutingContext;

public class GetGameDetailsRequest extends BaseSplendorRequest {

    public GetGameDetailsRequest(RoutingContext ctx) {
        super(ctx);
    }

    public int getGameId() {
        return params.pathParameter("gameId").getInteger();
    }
}
