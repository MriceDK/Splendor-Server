package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Development;
import be.howest.ti.game.web.views.DevelopmentInListView;
import java.util.ArrayList;
import java.util.List;


public class getDevelopmentsResponse extends AbstractResponseWithHiddenStatus{
    private final List<DevelopmentInListView> developments = new ArrayList<>();

    public getDevelopmentsResponse(List<Development> developments) {
        super(200);
        for (Development development : developments) {
            this.developments.add(new DevelopmentInListView(development));
        }
    }

    public List<DevelopmentInListView> getDevelopments() {
        return developments;
    }
}
