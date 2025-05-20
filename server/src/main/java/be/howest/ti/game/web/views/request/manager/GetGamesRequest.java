package be.howest.ti.game.web.views.request.manager;

import be.howest.ti.game.web.views.request.BaseSplendorRequest;
import io.vertx.ext.web.RoutingContext;

public class GetGamesRequest extends BaseSplendorRequest {


    public GetGamesRequest(RoutingContext ctx) {
        super(ctx);
    }

    public boolean getStarted() {
        return params.queryParameter("started").getBoolean();
    }
}
