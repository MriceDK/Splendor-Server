package be.howest.ti.game.web.views.response.operations;

import be.howest.ti.game.logic.Noble;
import be.howest.ti.game.web.views.NobleInSetView;
import be.howest.ti.game.web.views.response.AbstractResponseWithHiddenStatus;

public class ChooseNobleResponse extends AbstractResponseWithHiddenStatus {

    private NobleInSetView noble;

    public ChooseNobleResponse(Noble noble) {
        super(200);
        this.noble = new NobleInSetView(noble);
    }

    public NobleInSetView getNoble() {
        return noble;
    }

}
