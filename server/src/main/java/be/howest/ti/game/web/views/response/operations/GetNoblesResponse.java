package be.howest.ti.game.web.views.response.operations;

import be.howest.ti.game.logic.Noble;
import be.howest.ti.game.web.views.NobleInListView;
import be.howest.ti.game.web.views.response.AbstractResponseWithHiddenStatus;

import java.util.ArrayList;
import java.util.List;

public class GetNoblesResponse extends AbstractResponseWithHiddenStatus {
    private final List<NobleInListView> noblesInListView;

    public GetNoblesResponse(List<Noble> nobles) {
        super(200);
        noblesInListView = convertDevelopmentToDevelopmentListView(nobles);
    }

    private List<NobleInListView> convertDevelopmentToDevelopmentListView(List<Noble> nobles) {
        List<NobleInListView> noblesConverted = new ArrayList<>();
        for (Noble noble : nobles) {
            noblesConverted.add(new NobleInListView(noble));
        }
        return noblesConverted;
    }

    public List<NobleInListView> getNobles() {
        return noblesInListView;
    }
}
