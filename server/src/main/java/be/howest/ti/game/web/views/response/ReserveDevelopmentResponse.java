package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Development;
import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.logic.Token;
import be.howest.ti.game.web.views.DevelopmentInListView;

import java.util.Map;

public class ReserveDevelopmentResponse extends AbstractResponseWithHiddenStatus{
    private final DevelopmentInListView reservedDevelopment;
    public ReserveDevelopmentResponse(Development reservedDevelopment) {
        super(200);
        this.reservedDevelopment = new DevelopmentInListView(reservedDevelopment);
    }

    public DevelopmentInListView getDevelopment() {
        return reservedDevelopment;
    }
}
