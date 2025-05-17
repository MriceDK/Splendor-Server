package be.howest.ti.game.logic;

import be.howest.ti.game.logic.service.SplendorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SplendorGameTest {

    private GameSuperclass lobby;

    @BeforeEach
    public void init() {
        lobby = new GameLobby(1, 2);
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
    void chooseNobleGood() {
        Player player = new Player("Gulf of Mexico");
        lobby.addPlayer("Gulf of Mexico");
        SplendorGame game = new SplendorGame(lobby);


        List<Noble> nobles = new ArrayList<>(game.getUnclaimedNobles());
        Noble possibleNobleToChoose1 = nobles.getFirst();
        Noble possibleNobleToChoose2 = nobles.getLast();
        game.setCurrentPlayer(player);
        Purse requiredBonuses = new Purse(Map.of(Token.DIAMOND, 5, Token.EMERALD, 5, Token.SAPPHIRE, 5, Token.ONYX, 5, Token.RUBY, 5));
        player.setBonuses(requiredBonuses);

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