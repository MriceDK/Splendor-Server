package be.howest.ti.game.web.views.response.operations;

import be.howest.ti.game.logic.Development;
import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.web.views.DevelopmentInListView;
import be.howest.ti.game.web.views.response.AbstractResponseWithHiddenStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReserveDevelopmentResponse extends AbstractResponseWithHiddenStatus {
    private final List<DevelopmentInListView> reservedDevelopments = new ArrayList<>();
    private final Purse tokens;

    public ReserveDevelopmentResponse(List<Development> reservedDevelopment, Purse tokens) {
        super(200);
        this.tokens = tokens;
        for (Development development : reservedDevelopment) {
            this.reservedDevelopments.add(new DevelopmentInListView(development));
        }
    }

    public List<DevelopmentInListView> getReserve() {
        return reservedDevelopments;
    }

    public Map<String, Integer> getTokens() {
        return Purse.toMapStringInteger(tokens.getAvailableTokens());
    }
}
