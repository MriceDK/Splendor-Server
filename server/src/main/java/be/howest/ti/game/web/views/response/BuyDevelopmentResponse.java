package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.logic.Token;
import be.howest.ti.game.web.views.DevelopmentInListView;

import java.util.List;
import java.util.Map;

public class BuyDevelopmentResponse extends AbstractResponseWithHiddenStatus {

    private static final int NO_VALUE_IN_TOKEN = 0;
    private final List<DevelopmentInListView> developments;
    private final Map<Token, Integer> tokens;

    public BuyDevelopmentResponse(List<DevelopmentInListView> developments, Map<Token, Integer> tokens) {
        super(200);
        this.developments = developments;
        this.tokens = tokens;

    }

    public List<DevelopmentInListView> getDevelopments() {
        return developments;
    }

    public Map<String, Integer> getTokens() {

        return new UpdateTokensResponse(new Purse(tokens)).getTokens();
    }
}
