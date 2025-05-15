package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Noble;

import java.util.List;

public class ChooseNobleResponse extends AbstractResponseWithHiddenStatus {

    private List<Noble> nobles;

    public ChooseNobleResponse(List<Noble> nobles) {
        super(200);
        this.nobles = nobles;
    }

    public List<Noble> getNobles() {
        return nobles;
    }

}
