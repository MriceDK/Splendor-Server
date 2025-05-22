package be.howest.ti.game.web.views.response.operations;

import be.howest.ti.game.logic.Development;
import be.howest.ti.game.web.views.DevelopmentInListView;
import be.howest.ti.game.web.views.response.AbstractResponseWithHiddenStatus;

import java.util.ArrayList;
import java.util.List;


public class GetDevelopmentsResponse extends AbstractResponseWithHiddenStatus {
    private final List<DevelopmentInListView> developmentsInListView;

    public GetDevelopmentsResponse(List<Development> developments) {
        super(200);
        developmentsInListView = convertDevelopmentToDevelopmentListView(developments);
    }

    private List<DevelopmentInListView> convertDevelopmentToDevelopmentListView(List<Development> developments) {
        List<DevelopmentInListView> developmentsConverted = new ArrayList<>();
        for (Development development : developments) {
            developmentsConverted.add(new DevelopmentInListView(development));
        }
        return developmentsConverted;
    }

    public List<DevelopmentInListView> getDevelopments() {
        return developmentsInListView;
    }
}
