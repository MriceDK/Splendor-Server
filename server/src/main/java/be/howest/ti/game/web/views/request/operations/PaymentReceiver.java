package be.howest.ti.game.web.views.request.operations;

import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.logic.Token;
import be.howest.ti.game.web.views.request.BaseSplendorRequest;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.Map;

public class PaymentReceiver extends BaseSplendorRequest {

    public PaymentReceiver(RoutingContext ctx) {
        super(ctx);
    }

    public Purse getPayment(JsonObject jsonObjectToIterate){
        Purse purse = new Purse();

        for (Map.Entry<String, Object> paymentToken : jsonObjectToIterate){

            Token tokenToAdd = tokenTranslator(paymentToken);
            int valueToAdd = (Integer) paymentToken.getValue();

            purse.addToken(tokenToAdd, valueToAdd);

        }
        return purse;
    }

    private static Token tokenTranslator(Map.Entry<String, Object> paymentToken) {
        return Token.valueOf(paymentToken.getKey().toUpperCase());
    }
}
