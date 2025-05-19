package be.howest.ti.game.logic;

import be.howest.ti.game.logic.exceptions.SplendorGameResourceNotFoundException;
import be.howest.ti.game.logic.exceptions.SplendorGameRuleException;
import be.howest.ti.game.util.reader.NobleReader;

import java.util.*;

public class SplendorGame extends GameSuperclass {

    private final static int TOKEN_AMOUNT_IN_TOKENBANK_FOR_FOUR_PLAYERS = 7;
    private final static int TOKEN_AMOUNT_IN_TOKENBANK_FOR_THREE_PLAYERS = 5;
    private final static int TOKEN_AMOUNT_IN_TOKENBANK_FOR_TWO_PLAYERS = 4;

    private final static int INITIAL_GOLD_AMOUNT = 5;

    private static final int ZERO_TOKENS = 0;
    private static final int MAX_DIFFERENT_TOKENS = 3;
    private static final int MAX_OF_SAME_TOKEN = 2;
    private static final int MIN_BANK_VALUE_FOR_TWO_OF_SAME_TOKENS = 4;

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

    private void checkIfActionCanBeCarriedOut(GameState wantedGameState) {
        if (!gameState.equals(wantedGameState)) {
            throw new SplendorGameRuleException("You can't do this at this point in the game");
        }
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
            throw new SplendorGameResourceNotFoundException("Player not found");
        }
        return requestPlayer;
    }

    public void buyDevelopment(Purse payment, String developmentName, Player player){
        checkIfActionCanBeCarriedOut(GameState.TURN_ACTION);

        if (playerTurnChecker(player)){
            Development development = market.findMatchingDevelopmentOverAllLevels(developmentName);
            player.buyDevelopment(market.removeVisibleDevelopment(development), payment);
            market.refillMarket(development.level());
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
        checkIfActionCanBeCarriedOut(GameState.TURN_ACTION);

        if (playerTurnChecker(player)) {
            Development development = market.findMatchingDevelopmentOverAllLevels(developmentName);
            player.reserveDevelopment(market.removeVisibleDevelopment(development));
            market.refillMarket(development.level());
            givePlayerGoldTokenIfPossible(player);

            endPhaseOfTurn(true);

        }
    }

    public void buyReservedDevelopment(Purse payment, String developmentName, Player player){
        checkIfActionCanBeCarriedOut(GameState.TURN_ACTION);

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
        checkIfActionCanBeCarriedOut(GameState.TURN_ACTION);

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
        checkIfActionCanBeCarriedOut(GameState.CHOOSE_NOBLE);

        playerTurnChecker(player);
        if (!unclaimedNobles.contains(noble)) {
            throw new SplendorGameResourceNotFoundException("Noble not available");
        }

        acquireNoble(noble);
        endTurn();
        return noble;
    }

    public Noble acquireNoble(Noble noble) {
        currentPlayer.claimNoble(noble);
        unclaimedNobles.remove(noble);
        return noble;
    }

    private boolean playerMeetsRequirements(Player player, Noble noble) {
        for (Token bonus : Token.values()) {
            int required = noble.neededBonuses().getTokens().get(bonus);
            int actual = player.getBonuses().getTokens().get(bonus);
            if (actual < required) {
                return false;
            }
        }
        return true;
    }

    public void acquireNoble(List<Noble> possibleNobles) {
        currentPlayer.claimNoble(possibleNobles.getFirst());
    }

    public void acquireTokens(Player player, Purse tokens){
        checkIfActionCanBeCarriedOut(GameState.TURN_ACTION);

        if(playerTurnChecker(player)){

            int sizeOfTokensToAcquire = tokens.getAvailableTokens().size();
            tokenBankRuleCheck(tokens);

            player.acquireTokens(tokens, sizeOfTokensToAcquire);
            tokenBank.removeTokens(tokens);

            endPhaseOfTurn(true);

        }
    }

    private void tokenBankRuleCheck(Purse tokensToAcquire){
        for (Map.Entry<Token, Integer> tokenToAdd : tokensToAcquire.getTokens().entrySet()) {
            if (tokenToAdd.getValue() == MAX_OF_SAME_TOKEN && tokenBank.getTokenValue(tokenToAdd.getKey()) < MIN_BANK_VALUE_FOR_TWO_OF_SAME_TOKENS) {
                throw new SplendorGameRuleException("You can only take two of the same token type if the bank has more than four available tokens");
            }
        }
    }

    public boolean playerTurnChecker(Player player) {
        if (!player.equals(currentPlayer)) {
            throw new SplendorGameRuleException("It's not this player's turn");
        } else {
            return true;
        }
    }

    public void returnTokens(Player player, Purse tokensToReturn) {
        checkIfActionCanBeCarriedOut(GameState.RETURN_GEMS);

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
