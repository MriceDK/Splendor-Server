package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Noble;

public class ChooseNobleResponse extends AbstractResponseWithHiddenStatus {

    private final Noble noble;

    public ChooseNobleResponse(Noble noble) {
        super(200);
        this.noble = noble;
    }
}
