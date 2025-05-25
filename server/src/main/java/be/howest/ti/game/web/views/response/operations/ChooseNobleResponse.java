package be.howest.ti.game.web.views.response.operations;

import be.howest.ti.game.logic.Noble;
import be.howest.ti.game.web.views.NobleInListView;
import be.howest.ti.game.web.views.response.AbstractResponseWithHiddenStatus;

public class ChooseNobleResponse extends AbstractResponseWithHiddenStatus {

    private NobleInListView noble;

    public ChooseNobleResponse(Noble noble) {
        super(200);
        this.noble = new NobleInListView(noble);
    }

    public NobleInListView getNoble() {
        return noble;
    }

}
