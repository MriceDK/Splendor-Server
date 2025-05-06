package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Development;
import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.web.views.DevelopmentInListView;

import java.util.List;

public class BuyDevelopmentResponse extends AbstractResponseWithHiddenStatus{

    private final List<DevelopmentInListView> developments;
    private final Purse payment;

    public BuyDevelopmentResponse(List<Development> development) {
        super(200);
        this.development = development;
        this.payment = payment;
    }

    public List<Development> getDevelopments(){
        return null;
    }
}
