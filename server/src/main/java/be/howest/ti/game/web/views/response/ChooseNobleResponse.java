package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Noble;

public class ChooseNobleResponse extends AbstractResponseWithHiddenStatus {

    private Noble noble;

    public ChooseNobleResponse(Noble noble) {
        super(200);
        this.noble = noble;
    }

    public Noble getNoble() {
        return noble;
    }

    public void setNoble(Noble noble) {
        this.noble = noble;
    }
}
