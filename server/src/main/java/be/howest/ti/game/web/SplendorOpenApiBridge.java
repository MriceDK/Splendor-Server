package be.howest.ti.game.web;

import be.howest.ti.game.logic.*;
import be.howest.ti.game.logic.service.GameManager;
import be.howest.ti.game.logic.service.GameOperations;
import be.howest.ti.game.logic.service.SplendorService;
import be.howest.ti.game.web.tokens.JsonWebToken;
//import be.howest.ti.game.web.tokens.PlainTextTokens;
// Import only to be used when working with PlainTextTokens instead of JsonWebToken
import be.howest.ti.game.web.tokens.SplendorHTTPPlayer;
import be.howest.ti.game.web.tokens.TokenManager;
import be.howest.ti.game.web.views.PlayerInListView;
import be.howest.ti.game.web.views.request.*;
import be.howest.ti.game.web.views.response.*;

import java.util.*;
import java.util.function.Supplier;

public class SplendorOpenApiBridge extends OpenApiBridge { // NOSONAR this is not a monster class, it is a bridge :-)

    private final Supplier<SplendorService> serviceFactory;

    public SplendorOpenApiBridge() {
        this(GameManager::new, new JsonWebToken());
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
    public GetGemsResponse getGems(GetGemsRequest request) {
        return new GetGemsResponse();
    }

    @Operation("get-nobles")
    public getNoblesResponse getNobles(getNoblesRequest request) {
        return new getNoblesResponse(GameOperations.getAllNobles());
    }

    @Operation("get-developments")
    public getDevelopmentsResponse getDevelopments(GetDevelopmentsRequest request) {
        return new getDevelopmentsResponse(GameOperations.getAllDevelopments());
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

        String token = createToken(new SplendorHTTPPlayer(game.getGameId(), request.getPlayerName()));

        return new CreateGameResponse(game, request.getPlayerName(), token);
    }

    @Operation("delete-games")
    public DeleteGamesResponse deleteGames(DeleteGamesRequest request) {
        SplendorService service = getService(request);
        return new DeleteGamesResponse(service.removeGames());
    }

    @Operation("get-game-details")
    public GetGameDetailsResponse getGameDetails(GetGameDetailsRequest request) { // TODO find a way to sort the response properties
        SplendorService service = getService(request);

        if (request.getAuthorizedGameId() != request.getGameId()) {
            throw new ForbiddenAccessException("The gameId in the path does not match the gameId in the token");
        }

        GameSuperclass game = service.findGame(
                request.getGameId()
        );
        if (!game.getPlayers().contains(new Player(request.getAuthorizedPlayerName()))) {
            throw new ForbiddenAccessException("Unauthorized");
        }

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
        GameSuperclass game = service.findGame(gameId);
        String token = createToken(new SplendorHTTPPlayer(game.getGameId(), request.getPlayerName()));


        return new JoinGameResponse(gameId, playerName, token);
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

        if (request.getAuthorizedGameId() != request.getGameId()) {
            throw new ForbiddenAccessException("The gameId in the path does not match the gameId in the token");
        }
        if (!request.getAuthorizedPlayerName().equals(request.getPlayerName())) {
            throw new ForbiddenAccessException("The playername in the path does not match the playerName in the token");
        }

        String playername = request.getPlayerName();
        int gameId = request.getGameId();
        boolean takeOrReturn = request.addOrReturnCheck();
        Map<Token, Integer> tokensToChange = request.getTokensToAdd();

        SplendorGame game = service.findStartedGame(gameId);
        Player player = game.findPlayer(playername);

        if (takeOrReturn) {
            game.acquireTokens(player, new Purse(tokensToChange));
        } else {
            game.returnTokens(player, new Purse(tokensToChange));
        }
        return new UpdateTokensResponse(player.getTokens());

    }

    @Operation("buy-development")
    public BuyDevelopmentResponse buyDevelopment(BuyDevelopmentRequest request) {

        SplendorService service = getService(request);

        if (request.getAuthorizedGameId() != request.getGameId()) {
            throw new ForbiddenAccessException("The gameId in the path does not match the gameId in the token");
        }
        if (!request.getAuthorizedPlayerName().equals(request.getPlayerName())) {
            throw new ForbiddenAccessException("The playername in the path does not match the playerName in the token");
        }

        SplendorGame game = service.findStartedGame(request.getGameId());
        Player player = game.findPlayer(request.getPlayerName());
        game.buyDevelopment(request.getPayment(), request.getDevelopmentName() , player);
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
            throw new ForbiddenAccessException("The gameId in the path does not match the gameId in the token");
        }
        if (!request.getAuthorizedPlayerName().equals(request.getPlayerName())) {
            throw new ForbiddenAccessException("The playername in the path does not match the playerName in the token");
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
    public BuyDevelopmentResponse buyReserveDevelopment(BuyReservedDevelopmentRequest request) {
        SplendorService service = getService(request);

        PlayerInListView playerView = new PlayerInListView(service.buyReservedDevelopment(request.getGameId(), request.getPlayerName(), request.getDevelopmentName(), request.getPayment()));
        return new BuyDevelopmentResponse(playerView.getBuilt(), playerView.getTokens());


    }

    @Operation("choose-noble")
    public ChooseNobleResponse chooseNoble(ChooseNobleRequest request) {
        SplendorService service = getService(request);

        int gameId = request.getGameId();

        SplendorGame game = service.findStartedGame(gameId);

        Player player = game.findPlayer(request.getPlayerName());

        Purse bonusPurse = request.getNeededBonuses();
        Noble noble = new Noble(request.getNobleName(), request.getPrestigePoints(), bonusPurse);

        Noble chosenNoble = game.chooseNoble(player, noble);

        return new ChooseNobleResponse(chosenNoble);
    }

    //endregion


}
