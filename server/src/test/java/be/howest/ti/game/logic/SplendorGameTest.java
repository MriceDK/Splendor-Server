package be.howest.ti.game.logic;

import be.howest.ti.game.logic.exceptions.SplendorGameRuleException;
import be.howest.ti.game.logic.exceptions.SplendorGameResourceNotFoundException;
import be.howest.ti.game.logic.service.SplendorService;
import be.howest.ti.game.logic.service.GameLobbyManager;
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

    private final String gameStateErrorMessage = "You can't do this at this point in the game";

    @BeforeEach
    public void init() {
        lobby = new GameLobby(1, 2);
        setupStartedGame();
    }

    public void setupStartedGame() {
        GameLobbyManager service = new GameLobbyManager();
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


        assertThrows(SplendorGameRuleException.class, () -> game.buyDevelopment(player.getTokens(), developmentToBuy.name(), player));

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
    public void checkForNobleGood() {

        lobby.addPlayer("Alice");
        lobby.addPlayer("John");
        SplendorGame game = new SplendorGame(lobby);
        Player player = game.getCurrentPlayer();

        Set<Noble> nobles = game.getUnclaimedNobles();
        List<Noble> nobleList = new ArrayList<>(nobles);
        Noble wantedNoble = nobleList.getFirst();

        player.getBonuses().addTokens(wantedNoble.neededBonuses());

        game.executeNobleClaimChecker();
        assertTrue(player.getAcquiredNobles().contains(wantedNoble));
    }

    @Test
    public void checkForNobleBad() {
        lobby.addPlayer("Alice");
        lobby.addPlayer("John");
        SplendorGame game = new SplendorGame(lobby);

        Player player = game.getCurrentPlayer();

        Set<Noble> nobles = game.getUnclaimedNobles();
        List<Noble> nobleList = new ArrayList<>(nobles);
        Noble wantedNoble = nobleList.getFirst();

        game.executeNobleClaimChecker();
        assertFalse(player.getAcquiredNobles().contains(wantedNoble));
    }

    @Test
    public void testAcquireValidTokens() {
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
    public void testAcquireTokensWrongPlayer() {
        lobby.addPlayer("Musk");
        lobby.addPlayer("Macron");
        SplendorGame game = new SplendorGame(lobby);
        Purse requested = new Purse();

        requested.addTokens(new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1)));

        assertThrows(SplendorGameRuleException.class, () -> game.acquireTokens(game.getPlayers().get(1), requested));
    }

    @Test
    public void testAcquireInvalidTokenCount() {
        lobby.addPlayer("Vance");
        SplendorGame game = new SplendorGame(lobby);
        Purse requested = new Purse();

        requested.addTokens(new Purse(Map.of(Token.DIAMOND, 2, Token.SAPPHIRE, 1)));

        assertThrows(SplendorGameRuleException.class, () -> game.acquireTokens(game.getCurrentPlayer(), requested));
    }

    @Test
    public void testAcquireTooManyTypes() {
        lobby.addPlayer("PM Greenland");
        Purse requested = new Purse();
        SplendorGame game = new SplendorGame(lobby);

        requested.addTokens(new Purse(Map.of(
                Token.DIAMOND, 1,
                Token.SAPPHIRE, 1,
                Token.EMERALD, 1,
                Token.RUBY, 1
        )));

        assertThrows(SplendorGameRuleException.class, () -> game.acquireTokens(game.getCurrentPlayer(), requested));
    }

    @Test
    public void acquiringTokensIsAllowedWhenGameStateIsTurnAction() {
        startedGame.acquireTokens(new Player("Alice"), new Purse(Map.of(Token.SAPPHIRE, 2)));
        startedGame.acquireTokens(new Player("Gert"), new Purse(Map.of(Token.RUBY, 2)));

        assertEquals(GameState.TURN_ACTION, startedGame.getGameState());
    }

    @Test
    public void acquiringTokensIsNotAllowedWhenGameStateIsNotTurnAction() {
        assertEquals(GameState.TURN_ACTION, startedGame.getGameState());

        startedGame.acquireTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 1, Token.ONYX, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.SAPPHIRE, 1, Token.ONYX, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.ONYX, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.ONYX, 1)));

        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), alice);
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), gert);
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), alice);

        assertEquals(GameState.RETURN_GEMS, startedGame.getGameState());
        assertEquals(alice, startedGame.getCurrentPlayer());
        Exception ex = assertThrows(SplendorGameRuleException.class, () -> startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 2))));
        assertEquals(gameStateErrorMessage ,ex.getMessage());
    }

    @Test
    public void reservingDevelopmentIsAllowedWhenGameStateIsTurnAction() {
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), new Player("Alice"));
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), new Player("Gert"));

        assertEquals(GameState.TURN_ACTION, startedGame.getGameState());
    }

    @Test
    public void reservingDevelopmentIsNotAllowedWhenGameStateIsNotTurnAction() {
        assertEquals(GameState.TURN_ACTION, startedGame.getGameState());

        startedGame.acquireTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 1, Token.ONYX, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.SAPPHIRE, 1, Token.ONYX, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.ONYX, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.ONYX, 1)));

        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), alice);
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), gert);
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), alice);

        assertEquals(GameState.RETURN_GEMS, startedGame.getGameState());
        assertEquals(alice, startedGame.getCurrentPlayer());
        Exception ex = assertThrows(SplendorGameRuleException.class, () -> startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), gert));
        assertEquals(gameStateErrorMessage ,ex.getMessage());
    }

    @Test
    public void buyingADevelopmentIsAllowedWhenGameStateIsTurnAction() {
        assertEquals(GameState.TURN_ACTION, startedGame.getGameState());

        startedGame.acquireTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 1, Token.ONYX, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.SAPPHIRE, 1, Token.ONYX, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.ONYX, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.ONYX, 1)));

        startedGame.getCurrentPlayer().getTokens().addTokens(startedGame.getMarket().getVisibleDevelopments(1).getFirst().cost());
        startedGame.buyDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().cost(), startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), alice);
    }

    @Test
    public void buyingADevelopmentIsNotAllowedWhenGameStateIsNotTurnAction() {
        assertEquals(GameState.TURN_ACTION, startedGame.getGameState());

        startedGame.acquireTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 1, Token.ONYX, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.SAPPHIRE, 1, Token.ONYX, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.ONYX, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.ONYX, 1)));

        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), alice);
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), gert);
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), alice);

        startedGame.getCurrentPlayer().getTokens().addTokens(startedGame.getMarket().getVisibleDevelopments(1).getFirst().cost());

        Exception ex = assertThrows(SplendorGameRuleException.class, () -> startedGame.buyDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().cost(), startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), alice));
        assertEquals(gameStateErrorMessage ,ex.getMessage());
    }

    @Test
    public void buyingAReservedDevelopmentIsAllowedWhenGameStateIsTurnAction() {
        assertEquals(GameState.TURN_ACTION, startedGame.getGameState());

        startedGame.acquireTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 1, Token.ONYX, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.SAPPHIRE, 1, Token.ONYX, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.ONYX, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.ONYX, 1)));

        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), alice);
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), gert);

        startedGame.getCurrentPlayer().getTokens().addTokens(startedGame.getCurrentPlayer().getReservedDevelopments().getFirst().cost());
        startedGame.buyReservedDevelopment(startedGame.getCurrentPlayer().getReservedDevelopments().getFirst().cost(), startedGame.getCurrentPlayer().getReservedDevelopments().getFirst().name(), alice);
    }

    @Test
    public void buyingAReservedDevelopmentIsNotAllowedWhenGameStateIsNotTurnAction() {
        assertEquals(GameState.TURN_ACTION, startedGame.getGameState());

        startedGame.acquireTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 1, Token.ONYX, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.SAPPHIRE, 1, Token.ONYX, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.ONYX, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.ONYX, 1)));

        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), alice);
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), gert);
        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), alice);

        assertEquals(GameState.RETURN_GEMS, startedGame.getGameState());

        startedGame.getCurrentPlayer().getTokens().addTokens(startedGame.getCurrentPlayer().getReservedDevelopments().getFirst().cost());

        Exception ex = assertThrows(SplendorGameRuleException.class, () -> startedGame.buyReservedDevelopment(startedGame.getCurrentPlayer().getReservedDevelopments().getFirst().cost(), startedGame.getCurrentPlayer().getReservedDevelopments().getFirst().name(), alice));
        assertEquals(gameStateErrorMessage ,ex.getMessage());
    }

    @Test
    public void nobleCannotBeChosenWhenGameStateIsNotChooseNoble() {
        assertEquals(GameState.TURN_ACTION, startedGame.getGameState());
        Exception ex = assertThrows(SplendorGameRuleException.class, () -> startedGame.chooseNoble(startedGame.getCurrentPlayer(), new Noble("test", 3, new Purse())));
        assertEquals(gameStateErrorMessage ,ex.getMessage());
    }

    @Test
    public void returningGemsIsOnlyAllowedWhenGameStateIsReturnGems() {
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 1, Token.ONYX, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.SAPPHIRE, 1, Token.ONYX, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.RUBY, 1)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.ONYX, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 1, Token.DIAMOND, 1, Token.ONYX, 1)));

        startedGame.reserveDevelopmentFromLevel(1, alice);
        startedGame.reserveDevelopmentFromLevel(1, gert);
        startedGame.reserveDevelopmentFromLevel(1, alice);

        assertEquals(GameState.RETURN_GEMS, startedGame.getGameState());
        startedGame.returnTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 1)));
        assertEquals(GameState.TURN_ACTION, startedGame.getGameState());
    }

    @Test
    public void returningGemsIsNotAllowedWhenGameStateIsNotReturnGems() {
        assertEquals(GameState.TURN_ACTION, startedGame.getGameState());
        Exception ex = assertThrows(SplendorGameRuleException.class, () -> startedGame.returnTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 1))));
        assertEquals(gameStateErrorMessage ,ex.getMessage());
    }
    @Test
    public void testAcquireTwoTokensWhenBankIsBelowOrAboveFour() {
        lobby.addPlayer("Alice");
        lobby.addPlayer("Bob");

        SplendorGame game = new SplendorGame(lobby);
        Purse requested = new Purse();

        requested.addTokens(new Purse(Map.of(Token.DIAMOND, 2)));
        game.acquireTokens(game.getCurrentPlayer(), requested);

        assertThrows(SplendorGameRuleException.class, () -> game.acquireTokens(game.getCurrentPlayer(), requested));
    }

    @Test
    void errorWhenTryingToTakeOnlyGold(){
        lobby.addPlayer("Alice");
        lobby.addPlayer("Bob");

        SplendorGame game = new SplendorGame(lobby);
        Purse requested = new Purse();

        requested.addTokens(new Purse(Map.of(Token.GOLD, 2)));

        assertThrows(SplendorGameRuleException.class, () -> game.acquireTokens(game.getCurrentPlayer(), requested));
        assertEquals(5, game.getTokenBank().getTokenValue(Token.GOLD));

    }

    @Test
    void errorWhenTryingToTakeGoldAndOthers(){
        lobby.addPlayer("Alice");
        lobby.addPlayer("Bob");

        SplendorGame game = new SplendorGame(lobby);
        Purse requested = new Purse();

        requested.addTokens(new Purse(Map.of(Token.GOLD, 1, Token.DIAMOND, 1, Token.EMERALD, 1)));

        assertThrows(SplendorGameRuleException.class, () -> game.acquireTokens(game.getCurrentPlayer(), requested));
        assertEquals(5, game.getTokenBank().getTokenValue(Token.GOLD));

    }


    @Test
    public void buyReservedDevelopmentGood() {
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
    public void buyReservedDevelopmentDevelopmentNotReserved(){
        lobby.addPlayer("Mitchel Robinson");
        SplendorGame game = new SplendorGame(lobby);
        Player player = game.getCurrentPlayer();

        Development notReservedDevelopment = game.getMarket().getVisibleDevelopments(2).getFirst();

        Purse payment = notReservedDevelopment.cost();
        player.getTokens().addTokens(payment);

        assertThrows(SplendorGameResourceNotFoundException.class, () -> game.buyReservedDevelopment(payment, notReservedDevelopment.name(), player));

    }

    @Test
    public void chooseNobleGoodAndGameStateShouldBeChooseNoble() {
        List<Noble> nobles = new ArrayList<>(startedGame.getUnclaimedNobles());
        Noble possibleNobleToChoose2 = nobles.getLast();
        Player player = startedGame.getCurrentPlayer();

        Purse requiredBonuses = new Purse(Map.of(Token.DIAMOND, 5, Token.EMERALD, 5, Token.SAPPHIRE, 5, Token.ONYX, 5, Token.RUBY, 5));
        player.getBonuses().addTokens(requiredBonuses);

        startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), startedGame.getCurrentPlayer());

        assertEquals(GameState.CHOOSE_NOBLE, startedGame.getGameState());
        startedGame.chooseNoble(player, possibleNobleToChoose2);
        assertTrue(player.getAcquiredNobles().contains(possibleNobleToChoose2));
    }

    @Test
    public void chooseNobleBad() {
        lobby.addPlayer("Taiwan");
        lobby.addPlayer("Japan");
        SplendorGame game = new SplendorGame(lobby);
        Player player = game.getCurrentPlayer();

        List<Noble> nobles = new ArrayList<>(game.getUnclaimedNobles());
        Noble nobleToChoose = nobles.getFirst();

        assertFalse(player.getAcquiredNobles().contains(nobleToChoose));
    }

    @Test
    public void calculateWinner() {
        // start game
        // No winner has been found yet
        assertNull(startedGame.getWinner());

        startedGame.acquireTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.EMERALD, 2)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.RUBY, 2)));

        // Some time passes... A lot of turns have been played.

        startedGame.getCurrentPlayer().buyDevelopment(new Development("test", 3, 5, Token.ONYX, new Purse()), new Purse());
        startedGame.getCurrentPlayer().buyDevelopment(new Development("test", 3, 5, Token.DIAMOND, new Purse()), new Purse());
        startedGame.getCurrentPlayer().buyDevelopment(new Development("test", 3, 3, Token.DIAMOND, new Purse()), new Purse());
        startedGame.getCurrentPlayer().getBonuses().addTokens(startedGame.getUnclaimedNobles().stream().toList().getFirst().neededBonuses());

        // The second player almost has enough prestige point to win the game!

        startedGame.acquireTokens(gert, new Purse(Map.of(Token.ONYX, 2)));

        // The second player claimed a noble, and is now a possible winner
        // Last round activated
        if (startedGame.getGameState().equals(GameState.CHOOSE_NOBLE)) {
            List<Noble> nobles = new ArrayList<>(startedGame.getUnclaimedNobles());
            try {
                startedGame.chooseNoble(gert, nobles.getFirst());
            } catch (SplendorGameRuleException e) {
                startedGame.chooseNoble(gert, nobles.getLast());
            }

        }

        startedGame.acquireTokens(alice, new Purse(Map.of(Token.DIAMOND, 2)));

        // It is now the turn of the second player, and since he started his turn with equal or more than 15 prestige points,
        // The game will now calculate its winner.

        assertEquals(GameState.WINNER_FOUND, startedGame.getGameState());
        assertEquals(gert, startedGame.getWinner());

        // No single action can be carried out anymore
        Exception ex1 = assertThrows(SplendorGameRuleException.class, () -> startedGame.acquireTokens(gert, new Purse(Map.of(Token.SAPPHIRE, 2))));
        assertEquals(gameStateErrorMessage ,ex1.getMessage());
        Exception ex2 = assertThrows(SplendorGameRuleException.class, () -> startedGame.reserveDevelopmentFromLevel(1, gert));
        assertEquals(gameStateErrorMessage ,ex2.getMessage());
        Exception ex3 = assertThrows(SplendorGameRuleException.class, () -> startedGame.reserveDevelopment(startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), gert));
        assertEquals(gameStateErrorMessage ,ex3.getMessage());
        Exception ex4 = assertThrows(SplendorGameRuleException.class, () -> startedGame.buyDevelopment(new Purse(), startedGame.getMarket().getVisibleDevelopments(1).getFirst().name(), gert));
        assertEquals(gameStateErrorMessage ,ex4.getMessage());
        Exception ex5 = assertThrows(SplendorGameRuleException.class, () -> startedGame.buyReservedDevelopment(new Purse(), "non-existent", gert));
        assertEquals(gameStateErrorMessage ,ex5.getMessage());
        Exception ex6 = assertThrows(SplendorGameRuleException.class, () -> startedGame.returnTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 1))));
        assertEquals(gameStateErrorMessage ,ex6.getMessage());

    }

    @Test
    public void acquireTokensGetsLogged() {
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.SAPPHIRE, 1, Token.EMERALD, 1, Token.ONYX, 1)));

        assertEquals("Alice", startedGame.getHistory().getLogs().getFirst().getPlayerName());
        assertEquals("took 2 Sapphire", startedGame.getHistory().getLogs().getFirst().getAction());

        assertEquals("Gert", startedGame.getHistory().getLogs().getLast().getPlayerName());
        assertEquals("took 1 Emerald | 1 Onyx | 1 Sapphire", startedGame.getHistory().getLogs().getLast().getAction());
    }

    @Test
    public void buyDevelopmentGetsLogged() {
        Development developmentToBuy = startedGame.getMarket().getVisibleDevelopments(1).getFirst();
        startedGame.getCurrentPlayer().getTokens().addTokens(developmentToBuy.cost());

        startedGame.buyDevelopment(developmentToBuy.cost(), developmentToBuy.name(), alice);

        assertEquals("Alice", startedGame.getHistory().getLogs().getFirst().getPlayerName());
        assertEquals("bought development " + developmentToBuy + " for " + developmentToBuy.cost(), startedGame.getHistory().getLogs().getFirst().getAction());
    }

    @Test
    public void reserveDevelopmentGetsLogged() {
        Development firstDevelopmentToReserve = startedGame.getMarket().getVisibleDevelopments(1).getFirst();
        Development secondDevelopmentToReserve = startedGame.getMarket().getVisibleDevelopments(2).getFirst();

        startedGame.reserveDevelopment(firstDevelopmentToReserve.name(), alice);
        startedGame.reserveDevelopment(secondDevelopmentToReserve.name(), gert);

        assertEquals("Alice", startedGame.getHistory().getLogs().getFirst().getPlayerName());
        assertEquals("reserved development " + firstDevelopmentToReserve, startedGame.getHistory().getLogs().getFirst().getAction());

        assertEquals("Gert", startedGame.getHistory().getLogs().getLast().getPlayerName());
        assertEquals("reserved development " + secondDevelopmentToReserve, startedGame.getHistory().getLogs().getLast().getAction());
    }

    @Test
    public void reserveDevelopmentFromLevelGetsLogged() {
        startedGame.reserveDevelopmentFromLevel(1, alice);
        startedGame.reserveDevelopmentFromLevel(1, gert);

        Development firstReservedDevelopment = alice.getReservedDevelopments().getFirst();
        Development secondReservedDevelopment = gert.getReservedDevelopments().getFirst();

        assertEquals("Alice", startedGame.getHistory().getLogs().getFirst().getPlayerName());
        assertEquals("reserved the top development from level " + firstReservedDevelopment.level() + " stack", startedGame.getHistory().getLogs().getFirst().getAction());

        assertEquals("Gert", startedGame.getHistory().getLogs().getLast().getPlayerName());
        assertEquals("reserved the top development from level " + secondReservedDevelopment.level() + " stack", startedGame.getHistory().getLogs().getLast().getAction());
    }

    @Test
    public void buyReservedDevelopmentGetsLogged() {
        Development firstDevelopmentToReserve = startedGame.getMarket().getVisibleDevelopments(1).getFirst();
        Development secondDevelopmentToReserve = startedGame.getMarket().getVisibleDevelopments(2).getFirst();

        startedGame.reserveDevelopment(firstDevelopmentToReserve.name(), alice);
        startedGame.reserveDevelopment(secondDevelopmentToReserve.name(), gert);

        alice.getTokens().addTokens(firstDevelopmentToReserve.cost());
        gert.getTokens().addTokens(secondDevelopmentToReserve.cost());

        startedGame.buyReservedDevelopment(firstDevelopmentToReserve.cost(), firstDevelopmentToReserve.name(), alice);
        startedGame.buyReservedDevelopment(secondDevelopmentToReserve.cost(), secondDevelopmentToReserve.name(), gert);

        assertEquals("Alice", startedGame.getHistory().getLogs().get(2).getPlayerName());
        assertEquals("bought reserved development " + firstDevelopmentToReserve + " for " + firstDevelopmentToReserve.cost(), startedGame.getHistory().getLogs().get(2).getAction());

        assertEquals("Gert", startedGame.getHistory().getLogs().get(3).getPlayerName());
        assertEquals("bought reserved development " + secondDevelopmentToReserve + " for " + secondDevelopmentToReserve.cost(), startedGame.getHistory().getLogs().get(3).getAction());
    }

    @Test
    public void returnTokensGetsLogged() {
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1, Token.EMERALD, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1, Token.EMERALD, 1)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1, Token.EMERALD, 1)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.DIAMOND, 1, Token.SAPPHIRE, 1, Token.EMERALD, 1)));
        startedGame.acquireTokens(alice, new Purse(Map.of(Token.ONYX, 2)));
        startedGame.acquireTokens(gert, new Purse(Map.of(Token.RUBY, 2)));

        startedGame.reserveDevelopmentFromLevel(1, alice);
        startedGame.reserveDevelopmentFromLevel(1, gert);
        startedGame.reserveDevelopmentFromLevel(1, alice);
        startedGame.reserveDevelopmentFromLevel(1, gert);
        startedGame.reserveDevelopmentFromLevel(1, alice);

        assertEquals("Alice", startedGame.getHistory().getLogs().get(11).getPlayerName());
        assertEquals("has too many tokens. Waiting for player to return tokens...", startedGame.getHistory().getLogs().get(11).getAction());

        startedGame.returnTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 1)));

        assertEquals("Alice", startedGame.getHistory().getLogs().get(12).getPlayerName());
        assertEquals("returned 1 Sapphire to the token bank", startedGame.getHistory().getLogs().get(12).getAction());
    }

    @Test
    public void acquireNobleGetsLogged() {
        Noble nobleToAcquire = new ArrayList<>(startedGame.getUnclaimedNobles()).getFirst();
        alice.getBonuses().addTokens(nobleToAcquire.neededBonuses());

        startedGame.acquireTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 1)));

        assertEquals("Alice", startedGame.getHistory().getLogs().get(1).getPlayerName());
        assertEquals("got visited by noble '" + nobleToAcquire.name() + "'", startedGame.getHistory().getLogs().get(1).getAction());
    }

    @Test
    public void playerHavingToChooseNobleGetsLogged() {
        Noble firstNobleToAcquire = new ArrayList<>(startedGame.getUnclaimedNobles()).getFirst();
        Noble secondNobleToAcquire = new ArrayList<>(startedGame.getUnclaimedNobles()).get(1);

        alice.getBonuses().addTokens(firstNobleToAcquire.neededBonuses());
        alice.getBonuses().addTokens(secondNobleToAcquire.neededBonuses());

        startedGame.acquireTokens(alice, new Purse(Map.of(Token.SAPPHIRE, 1)));

        assertEquals("Alice", startedGame.getHistory().getLogs().get(1).getPlayerName());
        assertEquals("needs to choose a noble...", startedGame.getHistory().getLogs().get(1).getAction());
    }

    @Test
    public void winnerHasBeenFoundGetsLogged() {
        calculateWinner();

        assertEquals("Gert", startedGame.getHistory().getLogs().getLast().getPlayerName());
        assertEquals("has won the game!", startedGame.getHistory().getLogs().getLast().getAction());
    }

}