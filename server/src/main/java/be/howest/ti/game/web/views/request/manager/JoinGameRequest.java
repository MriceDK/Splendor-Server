package be.howest.ti.game.web.views.request.manager;

import be.howest.ti.game.util.customization.CountryFlag;
import be.howest.ti.game.web.views.request.BaseSplendorRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class JoinGameRequest extends BaseSplendorRequest {
    public JoinGameRequest(RoutingContext ctx) {
        super(ctx);
    }

    public int getGameId() {
        return params.pathParameter("gameId").getInteger();
    }

    public String getPlayerName() {
        return params.pathParameter("playerName").getString();
    }

    public boolean getIsSpectator() {
        // ctx is used because it allows arguments to be passed on which are not in the spec
        // this is a bonus functionality

        try {
            return ctx.body().asJsonObject().getBoolean("isSpectator");
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public boolean getWantsToLeave() {
        // ctx is used because it allows arguments to be passed on which are not in the spec
        // this is a bonus functionality
        try {
            return ctx.body().asJsonObject().getBoolean("wantsToLeave");
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public CountryFlag getAvatar(){
        // this is a bonus functionality
        try {
            String countryCode = ctx.body().asJsonObject().getString("avatar");
            return countryCodeTranslator(countryCode);
        } catch (NullPointerException ex){
            return CountryFlag.BE;
        }

    }

    private CountryFlag countryCodeTranslator(String countryCode){
        return CountryFlag.valueOf(countryCode.toUpperCase());
    }

    public String getToken() {
        if (!ctx.request().getHeader("Authorization").isEmpty()) {
            return ctx.request().getHeader("Authorization");
        }
        throw new IllegalArgumentException("Authorization token is missing");
    }
}
