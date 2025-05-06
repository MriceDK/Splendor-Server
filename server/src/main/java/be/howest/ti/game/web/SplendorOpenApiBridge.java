package be.howest.ti.game.web;

import be.howest.ti.game.logic.GameLobby;
import be.howest.ti.game.logic.GameSuperclass;
import be.howest.ti.game.logic.service.SplendorService;
import be.howest.ti.game.logic.service.SplendorServiceImpl;
import be.howest.ti.game.web.tokens.PlainTextTokens;
import be.howest.ti.game.web.tokens.TokenManager;
import be.howest.ti.game.web.views.request.*;
import be.howest.ti.game.web.views.response.*;

import java.util.*;
import java.util.function.Supplier;

public class SplendorOpenApiBridge extends OpenApiBridge { // NOSONAR this is not a monster class, it is a bridge :-)

    private final Supplier<SplendorService> serviceFactory;

    public SplendorOpenApiBridge() {
        this(SplendorServiceImpl::new, new PlainTextTokens());
    }

    // Factory needed to differentiate between group-tokens, can be simplified with a single service in the student version.
    SplendorOpenApiBridge(Supplier<SplendorService> serviceFactory, TokenManager tokenManager) {
        installPlayerTokenManager(tokenManager);
        this.serviceFactory = serviceFactory;
    }

    private final Map<String, SplendorService> services = new HashMap<>();

    private SplendorService getService(ContextBasedRequestView request) {
        return services.computeIfAbsent(request.getGroupSecret().toString(),
                k -> serviceFactory.get());
    }

    //region Generic Checks
    private static String ensureUrlSafe(String type, String txt) {
        if (!txt.matches("[a-zA-Z0-9]+")) { // letters and digits only or throw
            throw new IllegalArgumentException(type + " should be alphanumeric because it can be used in the url");
        }
        return txt;
    }
    //end region

    //region General operations

    @Operation("get-info")
    public NotYetImplementedResponse getInfo(BaseSplendorRequest request) {
        return new NotYetImplementedResponse("get-info");
    }

    @Operation("get-gems")
    public NotYetImplementedResponse getGems(BaseSplendorRequest request) {
        return new NotYetImplementedResponse("get-gems");
    }

    @Operation("get-nobles")
    public NotYetImplementedResponse getNobles(BaseSplendorRequest request) {
        return new NotYetImplementedResponse("get-nobles");
    }

    @Operation("get-developments")
    public NotYetImplementedResponse getDevelopments(BaseSplendorRequest request) {
        return new NotYetImplementedResponse("get-developments");
    }

    //endregion

    //region Game Management operations

    @Operation("get-games")
    public GetGamesResponse getGames(GetGamesRequest request) {
        SplendorService service = getService(request);

        List<GameSuperclass> games;

        try {
            if (request.getStarted()) {
                games = service.getGames(true);
            } else {
                games = service.getGames(false);
            }
        } catch (NullPointerException ex) {
            games = service.getGames();
        }


        return new GetGamesResponse(games);
    }

    @Operation("create-game")
    public CreateGameResponse createGame(CreateGameRequest request) {
        SplendorService service = getService(request);

        GameLobby game;
        if (request.getGameName() == null) {
            game = service.createLobby(
                    request.getNumberOfPlayers(),
                    request.getPlayerName()
            );
        } else {
            game = service.createLobby(
                    request.getNumberOfPlayers(),
                    request.getPlayerName(),
                    request.getGameName()
            );
        }

        return new CreateGameResponse(game, request.getPlayerName());
    }

    @Operation("delete-games")
    public NotYetImplementedResponse deleteGames(BaseSplendorRequest request) {
        return new NotYetImplementedResponse("delete-games");
    }

    @Operation("get-game-details")
    public GetGameDetailsResponse getGameDetails(GetGameDetailsRequest request) {
        SplendorService service = getService(request);



        return new GetGameDetailsResponse();
    }

    @Operation("join-game")
    public JoinGameResponse joinGame(JoinGameRequest request) {

        SplendorService service = getService(request);
        String playerName = request.getPlayerName();
        int gameId = request.getGameId();
        service.joinLobby(service.findLobby(gameId), playerName);
        return new JoinGameResponse(gameId, playerName);
    }

    //endregion

    //region Player Resources operations
    @Operation("get-player-details")
    public NotYetImplementedResponse getPlayerDetails(BaseSplendorRequest request) {
        return new NotYetImplementedResponse("get-player-details");
    }

    //endregion

    //region Game Action operations
    @Operation("update-tokens")
    public NotYetImplementedResponse updateTokens(BaseSplendorRequest request) {
        return new NotYetImplementedResponse("update-tokens");
    }

    @Operation("buy-development")
    public NotYetImplementedResponse buyDevelopment(BaseSplendorRequest request) {
        return new NotYetImplementedResponse("buy-development");
    }

    @Operation("reserve-development")
    public NotYetImplementedResponse reserveDevelopment(BaseSplendorRequest request) {
        return new NotYetImplementedResponse("reserve-development");
    }

    @Operation("buy-reserved-development")
    public NotYetImplementedResponse buyReserveDevelopment(BaseSplendorRequest request) {
        return new NotYetImplementedResponse("buy-reserved-development");
    }

    @Operation("choose-noble")
    public NotYetImplementedResponse chooseNoble(BaseSplendorRequest request) {
        return new NotYetImplementedResponse("choose-noble");
    }
    //endregion


}
