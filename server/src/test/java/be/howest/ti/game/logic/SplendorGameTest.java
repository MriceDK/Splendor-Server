package be.howest.ti.game.logic;

import be.howest.ti.game.logic.service.SplendorService;
import be.howest.ti.game.logic.service.GameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SplendorGameTest {

    private GameSuperclass lobby;
    private SplendorGame startedGame;
    private Player alice;
    private Player gert;

    @BeforeEach
    public void init() {
        lobby = new GameLobby(1, 2);
        setupStartedGame();
    }

    public void setupStartedGame() {
        SplendorService service = new GameManager();
        GameLobby unstartedGame = service.createLobby(2, "Alice");
        service.joinLobby(unstartedGame, "Gert");
        startedGame = service.findStartedGame(0);

        alice = startedGame.getPlayers().getFirst();
        gert = startedGame.getPlayers().get(1);
    }

    @Test
    public void copyConstructor() {
        lobby.addPlayer("Bobby");
        SplendorGame game = new SplendorGame(lobby);
        assertEquals(1, game.getGameId());
        assertNull(game.getGameName());
        assertEquals(1, game.getTotalPlayers());
    }

    @Test
    public void reserveDevelopment() {
        lobby.addPlayer("Bobby");
        SplendorGame startedGame = new SplendorGame(lobby);
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), startedGame.findPlayer("Bobby"));
        assertEquals(1,startedGame.getCurrentPlayer().getReservedDevelopments().size());
    }

    @Test
    public void reserveDevelopmentFromLevel() {
        lobby.addPlayer("Bobby");
        SplendorGame startedGame = new SplendorGame(lobby);
        startedGame.reserveDevelopmentFromLevel(1, startedGame.findPlayer("Bobby"));
        assertEquals(1,startedGame.getCurrentPlayer().getReservedDevelopments().size());
    }

    @Test
    public void buyDevelopment() {
        lobby.addPlayer("Bobby");
        SplendorGame startedGame = new SplendorGame(lobby);
        Development firstDevelopment = startedGame.getMarket().getVisibleDevelopments(1).getFirst();
        startedGame.getCurrentPlayer().getTokens().addTokens(firstDevelopment.cost());


        Purse payment = firstDevelopment.cost();
        startedGame.buyDevelopment(payment, startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), startedGame.getCurrentPlayer());
        assertEquals(1,startedGame.getCurrentPlayer().getBonuses().getTokens().get(firstDevelopment.bonus()));
    }

    @Test
    public void buyDevelopmentNotEnoughTokens(){
        lobby.addPlayer("Watergate concierge");
        SplendorGame game = new SplendorGame(lobby);
        Player player = game.getCurrentPlayer();

        Development developmentToBuy = game.getMarket().getVisibleDevelopments(1).getFirst();


        assertThrows(IllegalArgumentException.class, () -> {
            game.buyDevelopment(player.getTokens(), developmentToBuy.name(), player);
        });

    }

    @Test
    public void buyDevelopmentTokenBankRefilled(){

        lobby.addPlayer("Bobby");
        SplendorGame startedGame = new SplendorGame(lobby);
        Purse tokenBankBefore = startedGame.getTokenBank();
        Development firstDevelopment = startedGame.getMarket().getVisibleDevelopments(1).getFirst();
        startedGame.getCurrentPlayer().getTokens().addTokens(firstDevelopment.cost());

        Purse payment = firstDevelopment.cost();
        startedGame.buyDevelopment(payment, startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), startedGame.getCurrentPlayer());
        Purse tokenBankAfter = startedGame.getTokenBank();
        assertEquals(tokenBankBefore, tokenBankAfter);

    }

    @Test
    void checkForNobleGood() {
        Player player = new Player("Alice");
        lobby.addPlayer("Alice");
        SplendorGame game = new SplendorGame(lobby);

        Set<Noble> nobles = game.getUnclaimedNobles();
        List<Noble> nobleList = new ArrayList<>(nobles);
        Noble wantedNoble = nobleList.getFirst();

        player.getBonuses().addTokens(wantedNoble.neededBonuses());
        game.setCurrentPlayer(player);

        game.checkForNoble();
        assertTrue(player.getAcquiredNobles().contains(wantedNoble));
    }

    @Test
    void checkForNobleBad() {
        Player player = new Player("Alice");
        lobby.addPlayer("Alice");
        SplendorGame game = new SplendorGame(lobby);

        Set<Noble> nobles = game.getUnclaimedNobles();
        List<Noble> nobleList = new ArrayList<>(nobles);
        Noble wantedNoble = nobleList.getFirst();

        game.setCurrentPlayer(player);

        game.checkForNoble();
        assertFalse(player.getAcquiredNobles().contains(wantedNoble));
    }

    @Test
    void testAcquireValidTokens() {
        lobby.addPlayer("Rutte");
        lobby.addPlayer("Francken");
        SplendorGame game = new SplendorGame(lobby);
        Purse requested = new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1, Token.EMERALD, 1));
        Player player = game.getCurrentPlayer();

        game.acquireTokens(player, requested);

        assertEquals(1, player.getTokens().getTokens().get(Token.DIAMOND));
        assertEquals(3, game.getTokenBank().getTokens().get(Token.DIAMOND));
    }

    @Test
    void testAcquireTokensWrongPlayer() {
        lobby.addPlayer("Musk");
        lobby.addPlayer("Macron");
        SplendorGame game = new SplendorGame(lobby);
        Purse requested = new Purse();

        requested.addTokens(new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1)));

        assertThrows(IllegalStateException.class, () -> game.acquireTokens(game.getPlayers().get(1), requested));
    }

    @Test
    void testAcquireInvalidTokenCount() {
        lobby.addPlayer("Vance");
        SplendorGame game = new SplendorGame(lobby);
        Purse requested = new Purse();

        requested.addTokens(new Purse(Map.of(Token.DIAMOND, 2, Token.SAPPHIRE, 1)));

        assertThrows(IllegalArgumentException.class, () -> game.acquireTokens(game.getCurrentPlayer(), requested));
    }

    @Test
    void testAcquireTooManyTypes() {
        lobby.addPlayer("PM Greenland");
        Purse requested = new Purse();
        SplendorGame game = new SplendorGame(lobby);

        requested.addTokens(new Purse(Map.of(
                Token.DIAMOND, 1,
                Token.SAPPHIRE, 1,
                Token.EMERALD, 1,
                Token.RUBY, 1
        )));

        assertThrows(IllegalArgumentException.class, () -> game.acquireTokens(game.getCurrentPlayer(), requested));
    }

    @Test
    void acquiringTokensIsAllowedWhenGameStateIsTurnAction() {
        startedGame.acquireTokens(new Player("Alice"), new Purse(Map.of(Token.SAPPHIRE, 2)));
        startedGame.acquireTokens(new Player("Gert"), new Purse(Map.of(Token.SAPPHIRE, 2)));

        assertEquals(GameState.TURN_ACTION, startedGame.getGameState());
    }

    @Test
    void acquiringTokensIsNotAllowedWhenGameStateIsNotTurnAction() {
        assertEquals(GameState.TURN_ACTION, startedGame.getGameState());

        startedGame.acquireTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.SAPPHIRE, 2)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.ONYX, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.ONYX, 2)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.RUBY, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.RUBY, 2)));

        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), alice);
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), gert);
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), alice);
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), gert);
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), alice);
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), gert);

        startedGame.acquireTokens(alice, new Purse(Map.of(Token.DIAMOND, 2)));

        assertEquals(GameState.RETURN_GEMS, startedGame.getGameState());
        assertEquals(alice, startedGame.getCurrentPlayer());
        assertThrows(IllegalStateException.class, () -> startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 2))));
    }

    @Test
    void reservingDevelopmentIsAllowedWhenGameStateIsTurnAction() {
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), new Player("Alice"));
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), new Player("Gert"));

        assertEquals(GameState.TURN_ACTION, startedGame.getGameState());
    }

    @Test
    void reservingDevelopmentIsNotAllowedWhenGameStateIsNotTurnAction() {
        assertEquals(GameState.TURN_ACTION, startedGame.getGameState());

        startedGame.acquireTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.SAPPHIRE, 2)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.ONYX, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.ONYX, 2)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.RUBY, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.RUBY, 2)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.DIAMOND, 2)));
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), gert);
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.EMERALD, 2)));
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), gert);
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), alice);

        assertEquals(GameState.RETURN_GEMS, startedGame.getGameState());
        assertEquals(alice, startedGame.getCurrentPlayer());
        assertThrows(IllegalStateException.class, () -> startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), alice));
    }

    @Test
    void buyingADevelopmentIsAllowedWhenGameStateIsTurnAction() {
        assertEquals(GameState.TURN_ACTION, startedGame.getGameState());

        startedGame.acquireTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.SAPPHIRE, 2)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.ONYX, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.ONYX, 2)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.RUBY, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.RUBY, 2)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.EMERALD, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 2)));

        buyValidDevelopment(startedGame.getMarket().getVisibleDevelopments(1), 0, 3, false);


    }

    @Test
    void buyingADevelopmentIsNotAllowedWhenGameStateIsNotTurnAction() {
        assertEquals(GameState.TURN_ACTION, startedGame.getGameState());

        startedGame.acquireTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.SAPPHIRE, 2)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.ONYX, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.ONYX, 2)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.RUBY, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.RUBY, 2)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.EMERALD, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 2)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.DIAMOND, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.DIAMOND, 2)));

        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), alice);

        assertThrows(IllegalStateException.class, () -> buyValidDevelopment(startedGame.getMarket().getVisibleDevelopments(1), 0, 3, false));


    }

    private void buyValidDevelopment(List<Development> developments, int number, int max, boolean reserved) {

        try {
            if (reserved) {
                // reserved
            } else {
                startedGame.buyDevelopment(developments.get(number).cost(), developments.get(number).name(), alice);
            }

        } catch (IllegalArgumentException e) {
            if (number == max ) {
                return;
            }
            number += 1;
            buyValidDevelopment(developments, number, max, reserved);

        }
    }

    @Test
    void buyReservedDevelopmentGood() {
        lobby.addPlayer("Kentavious Cadwell-Pope");
        SplendorGame game = new SplendorGame(lobby);
        Player player = game.getCurrentPlayer();

        Development developmentToReserve = game.getMarket().getVisibleDevelopments(1).getFirst();
        player.reserveDevelopment(developmentToReserve);

        Purse payment = developmentToReserve.cost();
        player.getTokens().addTokens(payment);

        game.buyReservedDevelopment(payment, developmentToReserve.name(), player);

        assertFalse(player.getReservedDevelopments().contains(developmentToReserve));
        assertTrue(player.getOwnedDevelopments().contains(developmentToReserve));

    }

    @Test
    void buyReservedDevelopmentDevelopmentNotReserved(){
        lobby.addPlayer("Mitchel Robinson");
        SplendorGame game = new SplendorGame(lobby);
        Player player = game.getCurrentPlayer();

        Development notReservedDevelopment = game.getMarket().getVisibleDevelopments(2).getFirst();

        Purse payment = notReservedDevelopment.cost();
        player.getTokens().addTokens(payment);

        assertThrows(IllegalArgumentException.class, () -> {game.buyReservedDevelopment(payment, notReservedDevelopment.name(), player);});




    }
    @Test
    void chooseNobleGood() {
        Player player = new Player("Gulf of Mexico");
        lobby.addPlayer("Gulf of Mexico");
        SplendorGame game = new SplendorGame(lobby);


        List<Noble> nobles = new ArrayList<>(game.getUnclaimedNobles());
        Noble possibleNobleToChoose1 = nobles.getFirst();
        Noble possibleNobleToChoose2 = nobles.getLast();
        game.setCurrentPlayer(player);
        Purse requiredBonuses = new Purse(Map.of(Token.DIAMOND, 5, Token.EMERALD, 5, Token.SAPPHIRE, 5, Token.ONYX, 5, Token.RUBY, 5));
        player.getBonuses().addTokens(requiredBonuses);

        game.chooseNoble(possibleNobleToChoose2);
        assertTrue(player.getAcquiredNobles().contains(possibleNobleToChoose2));
    }

    @Test
    void chooseNobleBad() {
        lobby.addPlayer("Taiwan");
        SplendorGame game = new SplendorGame(lobby);
        Player player = game.getCurrentPlayer();

        List<Noble> nobles = new ArrayList<>(game.getUnclaimedNobles());
        Noble nobleToChoose = nobles.getFirst();
        game.setCurrentPlayer(player);

        assertFalse(player.getAcquiredNobles().contains(nobleToChoose));
    }
}