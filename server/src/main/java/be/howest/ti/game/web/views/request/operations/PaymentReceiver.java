package be.howest.ti.game.web.views.request.operations;

import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.logic.Token;
import be.howest.ti.game.web.views.request.BaseSplendorRequest;
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

            Token tokenToAdd = tokenTranslator(paymentToken);
            int valueToAdd = (Integer) paymentToken.getValue();

            mapToAdd.put(tokenToAdd, valueToAdd);

        }
        return new Purse(mapToAdd);
    }

    private static Token tokenTranslator(Map.Entry<String, Object> paymentToken) {
        return Token.valueOf(paymentToken.getKey().toUpperCase());
    }
}
