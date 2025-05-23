package be.howest.ti.game.web.views.response.operations;

import be.howest.ti.game.logic.Noble;
import be.howest.ti.game.web.views.NobleInSetView;
import be.howest.ti.game.web.views.response.AbstractResponseWithHiddenStatus;

import java.util.ArrayList;
import java.util.List;

public class GetNoblesResponse extends AbstractResponseWithHiddenStatus {
    private final List<NobleInSetView> noblesInListView;

    public GetNoblesResponse(List<Noble> nobles) {
        super(200);
        noblesInListView = convertDevelopmentToDevelopmentListView(nobles);
    }

    private List<NobleInSetView> convertDevelopmentToDevelopmentListView(List<Noble> nobles) {
        List<NobleInSetView> noblesConverted = new ArrayList<>();
        for (Noble noble : nobles) {
            noblesConverted.add(new NobleInSetView(noble));
        }
        return noblesConverted;
    }

    public List<NobleInSetView> getNobles() {
        return noblesInListView;
    }
}
