package be.howest.ti.game.web.views.request.operations;

import be.howest.ti.game.web.views.request.BaseSplendorRequest;
import io.vertx.ext.web.RoutingContext;

public class GetDevelopmentsRequest extends BaseSplendorRequest {
    public GetDevelopmentsRequest(RoutingContext ctx) {
        super(ctx);
    }
}
