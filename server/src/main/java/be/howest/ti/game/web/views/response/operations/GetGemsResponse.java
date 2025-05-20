package be.howest.ti.game.web.views.response.operations;

import be.howest.ti.game.logic.Token;
import be.howest.ti.game.web.views.response.AbstractResponseWithHiddenStatus;

public class GetGemsResponse extends AbstractResponseWithHiddenStatus {

    public GetGemsResponse() {
        super(200);
    }

    public String[] getGems() {
        String[] gems = new String[Token.values().length];

        for (int i = 0; i < gems.length; i++) {
            gems[i] = Token.values()[i].toString();
        }

        return gems;
    }
}
