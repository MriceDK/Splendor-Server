package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Development;
import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.web.views.DevelopmentInListView;

import java.util.List;

public class BuyDevelopmentResponse extends AbstractResponseWithHiddenStatus{

    private final List<Development> developments;

    public BuyDevelopmentResponse(List<Development> developments) {
        super(200);
        this.developments = developments;

    }

    public List<Development> getDevelopments(){
        return developments;
    }
}
