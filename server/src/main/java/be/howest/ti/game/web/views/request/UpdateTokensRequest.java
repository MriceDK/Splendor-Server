package be.howest.ti.game.web.views.request;

import be.howest.ti.game.logic.Token;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.Map;

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

    public Map<Token, Integer> getTokensToAdd() {

        Map<Token, Integer> mapTokensToAdd = new HashMap<>();
        JsonObject jsonObj = getAddOrReturn();
        for (Map.Entry<String, Object> tokenToTake : jsonObj) {

            Token tokenToAdd = Token.valueOf(tokenToTake.getKey().toUpperCase());
            int valueToAdd = (Integer) tokenToTake.getValue();

            mapTokensToAdd.put(tokenToAdd, valueToAdd);

        }

        return mapTokensToAdd;
    }

    private JsonObject getAddOrReturn() {
        JsonObject json = params.body().getJsonObject();
        return json.getJsonObject("return", json.getJsonObject("take"));
    }

    public boolean addOrReturnCheck() {
        JsonObject json = params.body().getJsonObject();
        if (json.containsKey("take")) {
            return true;
        } else if (json.containsKey("return")) {
            return false;
        } else {
            throw new IllegalStateException("A bad JSON Object was provided");
        }
    }
}
