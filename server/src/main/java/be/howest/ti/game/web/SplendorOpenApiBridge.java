package be.howest.ti.game.web;

import be.howest.ti.game.logic.*;
import be.howest.ti.game.logic.service.SplendorService;
import be.howest.ti.game.logic.service.SplendorServiceImpl;
import be.howest.ti.game.web.tokens.PlainTextTokens;
import be.howest.ti.game.web.tokens.TokenManager;
import be.howest.ti.game.web.views.PlayerInListView;
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
    public GetInfoResponse getInfo(GetInfoRequest request) {
        return new GetInfoResponse();
    }

    @Operation("get-gems")
    public NotYetImplementedResponse getGems(BaseSplendorRequest request) {
        SplendorService service = getService(request);

        return new NotYetImplementedResponse("get-gems");
    }

    @Operation("get-nobles")
    public NotYetImplementedResponse getNobles(BaseSplendorRequest request) {
        return new NotYetImplementedResponse("get-nobles");
    }

    @Operation("get-developments")
    public getDevelopmentsResponse getDevelopments(GetDevelopmentsRequest request) {
        SplendorService service = getService(request);
        return new getDevelopmentsResponse(service.getAllDevelopments());
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
    public GetGameDetailsResponse getGameDetails(GetGameDetailsRequest request) { // TODO find a way to sort the response properties
        SplendorService service = getService(request);

        GameSuperclass game = service.findGame(
                request.getGameId()
        );

        if (game.hasStarted()) {
            return new GetGameDetailsStartedResponse(game);
        } else {
            return new GetGameDetailsUnstartedResponse(game);
        }
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
    public UpdateTokensResponse updateTokens(UpdateTokensRequest request) {
        SplendorService service = getService(request);

        String playername = request.getPlayerName();
        int gameId = request.getGameId();
        boolean takeOrReturn = request.addOrReturnCheck();
        Map<Token, Integer> tokensToChange = request.getTokensToAdd();

        SplendorGame game = service.findStartedGame(gameId);
        Player player = game.findPlayer(playername);

        if (takeOrReturn){
            game.acquireTokens(player, new Purse(tokensToChange));
        } else {
            game.returnTokens(player, new Purse(tokensToChange));
        }
        return new UpdateTokensResponse(player.getTokens());

    }

    @Operation("buy-development")
    public BuyDevelopmentResponse buyDevelopment(BuyDevelopmentRequest request) {

        SplendorService service = getService(request);
        SplendorGame game = service.findStartedGame(request.getGameId());
        Player activePlayer = game.getCurrentPlayer();

        game.buyDevelopment(request.getPayment(), request.getDevelopmentName() , activePlayer);

        PlayerInListView activePlayerView = new PlayerInListView(activePlayer);
        return new BuyDevelopmentResponse(activePlayerView.getBuilt(), activePlayer.getTokens().getTokens());
    }

    @Operation("reserve-development")
    public ReserveDevelopmentResponse reserveDevelopment(ReserveDevelopmentRequest request) {
        SplendorService service = getService(request);

        int level = -1;
        String name = null;
        try {
            level = request.getDevelopmentLevel();
        } catch (IllegalArgumentException e) {
            try {
                name = request.getDevelopmentName();
            } catch (IllegalArgumentException err) {
                throw new IllegalArgumentException("Please provide a level (1-3) or a development name");
            }
        }

        SplendorGame game = service.findStartedGame(request.getGameId());
        Player player = game.findPlayer(request.getPlayerName());

        if (name != null) {
            game.reserveDevelopment(name, player);
            return new ReserveDevelopmentResponse(player.getReservedDevelopments(), player.getTokens());
        } else if (0 < level && level <= 3) {
            game.reserveDevelopmentFromLevel(level, player);
            return new ReserveDevelopmentResponse(player.getReservedDevelopments(), player.getTokens());
        } else throw new IllegalArgumentException("Please provide a valid level (1-3) or a development name");
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
