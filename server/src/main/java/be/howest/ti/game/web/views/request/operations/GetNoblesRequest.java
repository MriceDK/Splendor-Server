package be.howest.ti.game.web.views.request.operations;

import be.howest.ti.game.web.views.request.BaseSplendorRequest;
import io.vertx.ext.web.RoutingContext;

public class GetNoblesRequest extends BaseSplendorRequest {
    public GetNoblesRequest(RoutingContext ctx) {
        super(ctx);
    }
}
