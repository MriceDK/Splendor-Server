package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Token;

public class GetGemsResponse extends AbstractResponseWithHiddenStatus {

    public GetGemsResponse() {
        super(200);
    }

    public String[] getGems() {
        String[] gems = new String[Token.values().length];

        for (int i = 0; i < gems.length; i++) {
            gems[i] = Token.values()[i].toDisplayName();
        }

        return gems;
    }
}
