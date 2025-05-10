package be.howest.ti.game.web.views.request;

import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.logic.Token;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UpdateTokensRequest extends BaseSplendorRequest {
    public UpdateTokensRequest(RoutingContext ctx) {
        super(ctx);
    }

    public int getGameId() {
        return params.pathParameter("gameId").getInteger();
    }

    public String getPlayerName() {
        return params.pathParameter("playerName").getString();
    }

    public Purse getKeyword(){

        JsonObject json = params.body().getJsonObject();
        JsonObject jsonObj =  json.getJsonObject("return", json.getJsonObject("take"));
        for (Token token : jsonObj){

        }
    }
}
