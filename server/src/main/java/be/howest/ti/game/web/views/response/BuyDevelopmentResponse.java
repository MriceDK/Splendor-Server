package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Development;
import be.howest.ti.game.logic.Purse;

public class BuyDevelopmentResponse extends AbstractResponseWithHiddenStatus{

    private final Development development;
    private final Purse payment;

    public BuyDevelopmentResponse(Development development, Purse payment) {
        super(200);
        this.development = development;
        this.payment = payment;
    }
}
