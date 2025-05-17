package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Noble;

import java.util.List;

public class ChooseNobleResponse extends AbstractResponseWithHiddenStatus {

    private Noble noble;

    public ChooseNobleResponse(Noble noble) {
        super(200);
        this.noble = noble;
    }

    public Noble getNoble() {
        return noble;
    }

}
