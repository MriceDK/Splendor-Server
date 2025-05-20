package be.howest.ti.game.web.views.response.manager;

import be.howest.ti.game.web.views.response.AbstractResponseWithHiddenStatus;

public class GetInfoResponse extends AbstractResponseWithHiddenStatus {
    public GetInfoResponse() {
        super(200);
    }

    public String getGroupName() {
        return "Group 11";
    }

    public String getDevelopers() {
        return "Simon, Yoni, Maurice, Rune, Lars, Ruben";
    }
}
