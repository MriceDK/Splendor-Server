package be.howest.ti.game.web.views.request;

import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.logic.Token;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.Map;

public class PaymentReceiver extends BaseSplendorRequest {

    public PaymentReceiver(RoutingContext ctx) {
        super(ctx);
    }

    public Purse getPayment(){
        Map<Token, Integer> mapToAdd = new HashMap<>();
        JsonObject jsonObjectToIterate = params.body().getJsonObject().getJsonObject("payment");

        for (Map.Entry<String, Object> paymentToken : jsonObjectToIterate){

            Token tokenToAdd = Token.valueOf(paymentToken.getKey().toUpperCase());
            int valueToAdd = (Integer) paymentToken.getValue();

            mapToAdd.put(tokenToAdd, valueToAdd);

        }
        return new Purse(mapToAdd);
    }
}
