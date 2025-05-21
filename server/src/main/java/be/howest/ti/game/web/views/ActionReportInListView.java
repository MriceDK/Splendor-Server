package be.howest.ti.game.web.views;

import be.howest.ti.game.util.logger.ActionReport;

public class ActionReportInListView {

    private final ActionReport actionReport;

    public ActionReportInListView(ActionReport actionReport) {
        this.actionReport = actionReport;
    }

    public String getPlayerName() {
        return actionReport.getPlayerName();
    }

    public String getAction() {
        return actionReport.getAction();
    }

    public String getTimeOfCreation() {
        return actionReport.getFormattedTimeOfCreation();
    }

}
