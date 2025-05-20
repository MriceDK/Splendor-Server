package be.howest.ti.game.web.views.request.operations;

import be.howest.ti.game.web.views.request.BaseSplendorRequest;
import io.vertx.ext.web.RoutingContext;

public class GetGemsRequest extends BaseSplendorRequest {

    public GetGemsRequest(RoutingContext ctx) {
        super(ctx);
    }
}
