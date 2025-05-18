package be.howest.ti.game.logic;

import be.howest.ti.game.util.reader.NobleReader;

import java.util.*;

public class SplendorGame extends GameSuperclass {

    private final static int TOKEN_AMOUNT_IN_TOKENBANK_FOR_FOUR_PLAYERS = 7;
    private final static int TOKEN_AMOUNT_IN_TOKENBANK_FOR_THREE_PLAYERS = 5;
    private final static int TOKEN_AMOUNT_IN_TOKENBANK_FOR_TWO_PLAYERS = 4;

    private final static int INITIAL_GOLD_AMOUNT = 5;

    private final Purse tokenBank;
    private final Set<Noble> unclaimedNobles;
    private final Market market;
    private Player currentPlayer;
    private GameState gameState;
    private Player winner;

    private static final int ONE_NOBLE = 1;

    public SplendorGame(GameSuperclass gameLobby) {
        super(gameLobby);

        this.market = new Market();
        this.currentPlayer = getPlayers().getFirst();
        this.tokenBank = generateTokenBank();
        this.unclaimedNobles = setUnclaimedNobles();
        this.gameState = GameState.TURN_ACTION;
        this.winner = null;
    }

    public Player getWinner() {
        return winner;
    }

    public void endTurn() {
        setGameState(GameState.TURN_ACTION);
        currentPlayer = getNextPlayer();

        if (currentPlayer.isWinnerWorthy()) {
            setGameState(GameState.WINNER_FOUND);
            winner = calculateWinner();
        }
    }

    private Player calculateWinner() {
        List<Player> rankListOfPlayers = getPlayers();

        rankListOfPlayers.sort(new PlayerPrestigePointsOrder());

        return rankListOfPlayers.getFirst();
    }

    private Player getNextPlayer() {
        List<Player> players = getPlayers();
        int currentPlayerIndex = players.indexOf(currentPlayer);
        int nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return players.get(nextPlayerIndex);
    }

    private void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    private void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Market getMarket() {
        return market;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player findPlayer(String playerName) {
        List<Player> players = getPlayers();
        Player requestPlayer = null;
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                requestPlayer = player;
            }
        }
        if (requestPlayer == null) {
            throw new IllegalArgumentException("Player not found");
        }
        return requestPlayer;
    }

    public void buyDevelopment(Purse payment, String developmentName, Player player){
        if (!gameState.equals(GameState.TURN_ACTION)) {
            throw new IllegalStateException("The game state does not align with what you want to do!");
        }

        if (playerTurnChecker(player)){
            player.buyDevelopment(market.removeVisibleDevelopment(developmentName), payment);
            tokenBank.addTokens(payment);
            endPhaseOfTurn(true);
        }
    }

    private void endPhaseOfTurn(boolean tokenOverflowShouldBeChecked) {

        if (currentPlayer.hasTooManyTokens() && tokenOverflowShouldBeChecked) {
            setGameState(GameState.RETURN_GEMS);
            return;
        }

        if (!executeNobleClaimChecker()) {
            return;
        }

        endTurn();
    }

    public void reserveDevelopment(String developmentName, Player player){
        if (!gameState.equals(GameState.TURN_ACTION)) {
            throw new IllegalStateException("The game state does not align with what you want to do!");
        }

        if (playerTurnChecker(player)) {
            player.reserveDevelopment(market.removeVisibleDevelopment(developmentName));
            givePlayerGoldTokenIfPossible(player);

            endPhaseOfTurn(true);

        }
    }

    public void buyReservedDevelopment(Purse payment, String developmentName, Player player){
        if (!gameState.equals(GameState.TURN_ACTION)) {
            throw new IllegalStateException("The game state does not align with what you want to do!");
        }

        if (playerTurnChecker(player)){
            Development development = player.findDevelopmentInReservedDevelopments(developmentName);
            player.buyDevelopment(development, payment);
            player.removeReservedDevelopment(development);
            tokenBank.addTokens(payment);

            endPhaseOfTurn(false);
        }
    }

    private void givePlayerGoldTokenIfPossible(Player player) {
        if (tokenBank.getTokens().get(Token.GOLD) > 0) {
            tokenBank.removeToken(Token.GOLD, 1);
            player.getTokens().addToken(Token.GOLD, 1);
        }
    }

    public void reserveDevelopmentFromLevel(int level, Player player){
        if (!gameState.equals(GameState.TURN_ACTION)) {
            throw new IllegalStateException("The game state does not align with what you want to do!");
        }

        if (playerTurnChecker(player)) {
            player.reserveDevelopment(market.takeTopDevelopment(level));
            givePlayerGoldTokenIfPossible(player);

            endPhaseOfTurn(true);

        }
    }

    public boolean executeNobleClaimChecker() {
        List<Noble> possibleNobles = getClaimableNobles();

        if (possibleNobles.isEmpty()) {
            return true;
        }

        return chooseNobleNecessaryCheck(possibleNobles);
    }

    private List<Noble> getClaimableNobles() {
        List<Noble> possibleNobles = new ArrayList<>();
        for (Noble noble : unclaimedNobles) {
            if (currentPlayer.meetsRequirementsToClaimNoble(noble)) {
                possibleNobles.add(noble);
            }

        }
        return possibleNobles;
    }

    private boolean chooseNobleNecessaryCheck(List<Noble> possibleNobles) {
        if (possibleNobles.size() > ONE_NOBLE) {
            setGameState(GameState.CHOOSE_NOBLE);
            return false;
        } else {
            acquireNoble(possibleNobles.getFirst());
            return true;
        }
    }

    public Noble chooseNoble(Player player, Noble noble) {

        if (!gameState.equals(GameState.CHOOSE_NOBLE)) {
            throw new IllegalStateException("The game state does not align with what you want to do!");
        }
        playerTurnChecker(player);
        if (!unclaimedNobles.contains(noble)) {
            throw new IllegalArgumentException("Noble not available");
        }

        acquireNoble(noble);
        endTurn();
        return noble;
    }

    public void acquireNoble(Noble noble) {
        currentPlayer.claimNoble(noble);
        unclaimedNobles.remove(noble);
    }

    public void acquireTokens(Player player, Purse tokens){
        if (!gameState.equals(GameState.TURN_ACTION)) {
            throw new IllegalStateException("The game state does not align with what you want to do!");
        }

        if(playerTurnChecker(player)){
            player.acquireTokens(tokens);
            tokenBank.removeTokens(tokens);

            endPhaseOfTurn(true);

        }
    }

    public boolean playerTurnChecker(Player player) {
        if (!player.equals(currentPlayer)) {
            throw new IllegalStateException("It's not this player's turn");
        } else {
            return true;
        }
    }

    public void returnTokens(Player player, Purse tokensToReturn) {

        if (!gameState.equals(GameState.RETURN_GEMS)) {
            throw new IllegalStateException("The game state does not align with what you want to do!");
        }

        if (playerTurnChecker(player)) {
            player.returnTokens(tokensToReturn);
            tokenBank.addTokens(tokensToReturn);

            endPhaseOfTurn(false);
        }

    }

    public Set<Noble> setUnclaimedNobles() {
        NobleReader nobleReader = new NobleReader();
        return nobleReader.getRandomNobles(getTotalPlayers());
    }

    public GameState getGameState() {
        return gameState;
    }

    public Set<Noble> getUnclaimedNobles() {
        return unclaimedNobles;
    }

    public Purse getTokenBank() {
        return tokenBank;
    }

    private Purse generateTokenBank() {
        int amountPerToken = 0;

        if (getTotalPlayers() == 4) {
            amountPerToken = TOKEN_AMOUNT_IN_TOKENBANK_FOR_FOUR_PLAYERS;
        } else if (getTotalPlayers() == 3) {
            amountPerToken = TOKEN_AMOUNT_IN_TOKENBANK_FOR_THREE_PLAYERS;
        } else if (getTotalPlayers() == 2) {
            amountPerToken = TOKEN_AMOUNT_IN_TOKENBANK_FOR_TWO_PLAYERS;
        }

        return generateTokenBank(amountPerToken);

    }

    private Purse generateTokenBank(int amountPerToken) {
        return new Purse(
                Map.of(
                        Token.DIAMOND, amountPerToken,
                        Token.EMERALD, amountPerToken,
                        Token.ONYX, amountPerToken,
                        Token.RUBY, amountPerToken,
                        Token.SAPPHIRE, amountPerToken,
                        Token.GOLD, INITIAL_GOLD_AMOUNT
                )
        );
    }
}
