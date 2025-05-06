package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.GameSuperclass;

public class GetGameDetailsResponse extends AbstractResponseWithHiddenStatus {

    private final GameSuperclass game;

    public GetGameDetailsResponse(GameSuperclass game) {
        super(200);
        this.game = game;
    }

}
