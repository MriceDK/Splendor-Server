package be.howest.ti.game.web.views.request.manager;

import be.howest.ti.game.util.customization.CountryCode;
import be.howest.ti.game.web.views.request.BaseSplendorRequest;
import io.vertx.ext.web.RoutingContext;

public class CountryCodeReceiver  extends BaseSplendorRequest {
    public CountryCodeReceiver(RoutingContext ctx) {
        super(ctx);
    }

    public CountryCode getAvatar(){
        // this is a bonus functionality
        try {
            String countryCode = ctx.body().asJsonObject().getString("avatar");
            return countryCodeTranslator(countryCode);
        } catch (NullPointerException ex){
            return CountryCode.UN;
        }

    }

    private CountryCode countryCodeTranslator(String countryCode) {
        try {
            return CountryCode.valueOf(countryCode.toUpperCase());

        } catch (IllegalArgumentException ex){
            return CountryCode.UN;
        }

    }
}

