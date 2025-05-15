package be.howest.ti.game.web.views.response;

public class deleteGamesResponse extends AbstractResponseWithHiddenStatus {
    public deleteGamesResponse() {
        super(200);
    }

    public String getResponse() {
        return "Games deleted successfully"; // Placeholder for now
    }
}
