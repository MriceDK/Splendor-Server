package be.howest.ti.game.web;

import be.howest.ti.game.logic.*;
import be.howest.ti.game.logic.service.GameOperations;
import be.howest.ti.game.logic.service.SplendorService;
import be.howest.ti.game.logic.service.SplendorServiceImpl;
import be.howest.ti.game.util.customization.CountryCode;
import be.howest.ti.game.web.tokens.JsonWebToken;
import be.howest.ti.game.web.tokens.SplendorHTTPPlayer;
import be.howest.ti.game.web.tokens.TokenManager;
import be.howest.ti.game.web.views.PlayerInListView;
import be.howest.ti.game.web.views.request.*;
import be.howest.ti.game.web.views.request.manager.*;
import be.howest.ti.game.web.views.request.operations.*;
import be.howest.ti.game.web.views.response.manager.JoinSpectateGameResponse;
import be.howest.ti.game.web.views.response.manager.LeaveGameResponse;
import be.howest.ti.game.web.views.response.manager.SpectateGameResponse;
import be.howest.ti.game.web.views.response.manager.*;
import be.howest.ti.game.web.views.response.operations.*;

import java.util.*;
import java.util.function.Supplier;

public class SplendorOpenApiBridge extends OpenApiBridge { // NOSONAR this is not a monster class, it is a bridge :-)

    private final Supplier<SplendorService> serviceFactory;
    private final TokenManager tokenManager;
    private static final String FORBIDDEN_ACCESS_RESPONSE = "Unauthorized";

    public SplendorOpenApiBridge() {
        this(SplendorServiceImpl::new, new JsonWebToken());
    }

    // Factory needed to differentiate between group-tokens, can be simplified with a single service in the student version.
    SplendorOpenApiBridge(Supplier<SplendorService> serviceFactory, TokenManager tokenManager) {
        installPlayerTokenManager(tokenManager);
        // HiJacking the  token manager to use it to parse the token since there's no way to access it otherwise
        // This is only needed for manipulating the endpoints that we have since we can't add or change the endpoints
        this.tokenManager = tokenManager;
        this.serviceFactory = serviceFactory;
    }

    private final Map<String, SplendorService> services = new HashMap<>();

    private SplendorService getService(ContextBasedRequestView request) {

        String groupSecret = "Group11-6470-184";
        if (!request.getGroupSecret().toString().equals(groupSecret)) {
            throw new ForbiddenAccessException("You are not allowed to access this group");
        }
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
    public GetGemsResponse getGems(GetGemsRequest request) {
        return new GetGemsResponse();
    }

    @Operation("get-nobles")
    public GetNoblesResponse getNobles(GetNoblesRequest request) {
        return new GetNoblesResponse(GameOperations.getAllNobles());
    }

    @Operation("get-developments")
    public GetDevelopmentsResponse getDevelopments(GetDevelopmentsRequest request) {
        return new GetDevelopmentsResponse(GameOperations.getAllDevelopments());
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
                    request.getPlayerName(),
                    request.getAvatar()
            );
        } else {
            game = service.createLobby(
                    request.getNumberOfPlayers(),
                    request.getPlayerName(),
                    request.getAvatar(),
                    request.getGameName()
            );
        }

        String token = createToken(new SplendorHTTPPlayer(game.getGameId(), request.getPlayerName()));

        return new CreateGameResponse(game, request.getPlayerName(), request.getAvatar(), token);
    }

    @Operation("delete-games")
    public DeleteGamesResponse deleteGames(DeleteGamesRequest request) {
        SplendorService service = getService(request);
        return new DeleteGamesResponse(service.removeGames());
    }

    @Operation("get-game-details")
    public GetGameDetailsResponse getGameDetails(GetGameDetailsRequest request) {
        SplendorService service = getService(request);

        if (request.getAuthorizedGameId() != request.getGameId()) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCESS_RESPONSE);
        }

        GameSuperclass game = service.findGame(
                request.getGameId()
        );

        if (!game.getPlayers().contains(new Player(request.getAuthorizedPlayerName())) && !game.getSpectators().contains(request.getAuthorizedPlayerName())) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCESS_RESPONSE);
        }

        if (game.hasStarted()) {
            ((SplendorGame) game).checkTimeEndTurn(); // Makes sure the amount of time left gets updates every time someone sends a request to this endpoint
            return new GetGameDetailsStartedResponse(game);
        } else {
            return new GetGameDetailsUnstartedResponse(game);
        }
    }

    @Operation("join-game")
    public JoinSpectateGameResponse joinGame(JoinGameRequest request) {

        SplendorService service = getService(request);

        String playerName = request.getPlayerName();
        int gameId = request.getGameId();
        String token = createToken(new SplendorHTTPPlayer(gameId, request.getPlayerName()));

        if (request.getWantsToLeave() && request.getIsSpectator()) {
            // delete spectator from game
            service.removeSpectator(gameId, playerName);
            return new LeaveGameResponse(gameId, playerName, service.findGame(gameId).hasStarted());
        }

        if (request.getWantsToLeave() && !request.getIsSpectator()) {
            // delete player from game
            // substring is used to remove "Bearer " from the token
            int bearerStringLength = 7;
            // validate token using token manager
            SplendorHTTPPlayer parsedToken = tokenManager.parseToken(request.getToken().substring(bearerStringLength));
            if (parsedToken.getGameId() != request.getGameId()) {
                throw new ForbiddenAccessException(FORBIDDEN_ACCESS_RESPONSE);
            }
            if (!parsedToken.getPlayerName().equals(request.getPlayerName())) {
                throw new ForbiddenAccessException(FORBIDDEN_ACCESS_RESPONSE);
            }

            boolean hasStarted = service.findGame(gameId).hasStarted();
            service.removePlayer(gameId, playerName);
            return new LeaveGameResponse(gameId, playerName, hasStarted);
        }

        if (!request.getWantsToLeave() && request.getIsSpectator()) {
            // add spectator to game
            service.spectateGame(gameId, playerName);
            return new SpectateGameResponse(gameId, playerName, token);
        }

        if (!request.getWantsToLeave() && !request.getIsSpectator()) {
            // add player to game
            CountryCode avatar = request.getAvatar();
            service.joinLobby(gameId, playerName, avatar);
            return new JoinGameResponse(gameId, playerName, token, avatar);
        }
        throw new IllegalArgumentException("Please provide a valid request");
    }

    //endregion

    //region Player Resources operations

    //endregion

    //region Game Action operations
    @Operation("update-tokens")
    public UpdateTokensResponse updateTokens(UpdateTokensRequest request) {
        SplendorService service = getService(request);

        if (request.getAuthorizedGameId() != request.getGameId()) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCESS_RESPONSE);
        }

        if (!request.getAuthorizedPlayerName().equals(request.getPlayerName())) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCESS_RESPONSE);
        }

        String playerName = request.getPlayerName();
        int gameId = request.getGameId();
        boolean takeOrReturn = request.addOrReturnCheck();
        Purse tokensToChange = request.getTokensToAdd();
        Player player = service.updateTokens(takeOrReturn, gameId, playerName, tokensToChange);

        return new UpdateTokensResponse(player.getTokens());

    }

    @Operation("buy-development")
    public BuyDevelopmentResponse buyDevelopment(BuyDevelopmentRequest request) {

        SplendorService service = getService(request);

        if (request.getAuthorizedGameId() != request.getGameId()) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCESS_RESPONSE);
        }
        if (!request.getAuthorizedPlayerName().equals(request.getPlayerName())) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCESS_RESPONSE);
        }

        Player player = service.buyDevelopment(request.getGameId(), request.getPlayerName(), request.getDevelopmentName(), request.getPaymentPurse());
        PlayerInListView activePlayerView = new PlayerInListView(player);
        return new BuyDevelopmentResponse(activePlayerView.getBuilt(), activePlayerView.getTokens());

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

        if (request.getAuthorizedGameId() != request.getGameId()) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCESS_RESPONSE);
        }
        if (!request.getAuthorizedPlayerName().equals(request.getPlayerName())) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCESS_RESPONSE);
        }

        if (name != null) {
            Player player = service.reserveDevelopment(request.getGameId(), request.getPlayerName(), name);
            return new ReserveDevelopmentResponse(player.getReservedDevelopments(), player.getTokens());
        } else if (0 < level && level <= 3) {
            Player player = service.reserveDevelopmentFromLevel(request.getGameId(), request.getPlayerName(), level);
            return new ReserveDevelopmentResponse(player.getReservedDevelopments(), player.getTokens());
        } else throw new IllegalArgumentException("Please provide a valid level (1-3) or a development name");
    }

    @Operation("buy-reserved-development")
    public BuyDevelopmentResponse buyReserveDevelopment(BuyReservedDevelopmentRequest request) {
        SplendorService service = getService(request);

        PlayerInListView playerView = new PlayerInListView(service.buyReservedDevelopment(request.getGameId(), request.getPlayerName(), request.getDevelopmentName(), request.getPaymentPurse()));
        return new BuyDevelopmentResponse(playerView.getBuilt(), playerView.getTokens());


    }

    @Operation("choose-noble")
    public ChooseNobleResponse chooseNoble(ChooseNobleRequest request) {
        SplendorService service = getService(request);

        Noble chosenNoble = service.chooseNoble(request.getGameId(), request.getPlayerName(), new Noble(request.getNobleName(), request.getPrestigePoints(), request.getNeededBonuses()));

        return new ChooseNobleResponse(chosenNoble);
    }

    //endregion


}
