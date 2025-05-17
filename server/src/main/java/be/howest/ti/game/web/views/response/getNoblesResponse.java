package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Noble;
import be.howest.ti.game.web.views.NobleInSetView;

import java.util.ArrayList;
import java.util.List;

public class getNoblesResponse extends AbstractResponseWithHiddenStatus {
    private final List<NobleInSetView> noblesInListView;

    public getNoblesResponse(List<Noble> nobles) {
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
