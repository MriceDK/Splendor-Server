package be.howest.ti.game.web.views.request.manager;

import be.howest.ti.game.web.views.request.BaseSplendorRequest;
import io.vertx.ext.web.RoutingContext;

public class DeleteGamesRequest extends BaseSplendorRequest {
    public DeleteGamesRequest(RoutingContext ctx) {
        super(ctx);
    }
}
