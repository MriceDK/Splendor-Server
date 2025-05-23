package be.howest.ti.game.web.views.response.operations;

import be.howest.ti.game.web.views.DevelopmentInListView;
import be.howest.ti.game.web.views.response.AbstractResponseWithHiddenStatus;

import java.util.List;
import java.util.Map;

public class BuyDevelopmentResponse extends AbstractResponseWithHiddenStatus {

    private final List<DevelopmentInListView> developments;
    private final Map<String, Integer> tokens;

    public BuyDevelopmentResponse(List<DevelopmentInListView> developments, Map<String, Integer> tokens) {
        super(200);
        this.developments = developments;
        this.tokens = tokens;

    }

    public List<DevelopmentInListView> getDevelopments() {
        return developments;
    }

    public Map<String, Integer> getTokens() {

       return tokens;
    }
}
