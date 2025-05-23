package be.howest.ti.game.logic;

import be.howest.ti.game.logic.exceptions.SplendorGameResourceNotFoundException;
import be.howest.ti.game.logic.exceptions.SplendorGameRuleException;
import be.howest.ti.game.logic.order.implementations.PlayerPrestigePointsOrder;
import be.howest.ti.game.util.logger.ActionReport;
import be.howest.ti.game.util.logger.Logger;
import be.howest.ti.game.util.reader.NobleReader;

import java.util.*;

public class SplendorGame extends GameSuperclass {

    private static final int TOKEN_AMOUNT_IN_TOKENBANK_FOR_FOUR_PLAYERS = 7;
    private static final int TOKEN_AMOUNT_IN_TOKENBANK_FOR_THREE_PLAYERS = 5;
    private static final int TOKEN_AMOUNT_IN_TOKENBANK_FOR_TWO_PLAYERS = 4;

    private static final int INITIAL_GOLD_AMOUNT = 5;

    private static final int MAX_OF_SAME_TOKEN = 2;
    private static final int MIN_BANK_VALUE_FOR_TWO_OF_SAME_TOKENS = 4;

    private final Purse tokenBank;
    private final List<Noble> unclaimedNobles;
    private final Market market;
    private Player currentPlayer;
    private GameState gameState;
    private boolean isLastRound;
    private Player winner;
    private final Logger history;

    private static final int ONE_NOBLE = 1;

    public SplendorGame(GameSuperclass gameLobby) {
        super(gameLobby);

        this.market = new Market();
        this.currentPlayer = getPlayers().getFirst();
        this.tokenBank = generateTokenBank();
        this.unclaimedNobles = setUnclaimedNobles();
        this.gameState = GameState.TURN_ACTION;
        this.isLastRound = false;
        this.winner = null;
        history = new Logger();
    }

    public Player getWinner() {
        return winner;
    }

    private void checkIfActionCanBeCarriedOut(GameState wantedGameState) {
        if (!gameState.equals(wantedGameState)) {
            throw new SplendorGameRuleException("You can't do this at this point in the game");
        }
    }

    public void startTurn(){
        if (currentPlayer.isWinnerWorthy() && isLastRound) {
            setGameState(GameState.WINNER_FOUND);
            winner = calculateWinner();
            history.log(new ActionReport(winner.getName(), "has won the game!"));
        }
    }

    public void endTurn() {
        if (currentPlayer.isWinnerWorthy()) {
            isLastRound = true;
        }
        setGameState(GameState.TURN_ACTION);
        currentPlayer = getNextPlayer();
        startTurn();
    }

    public boolean getIsLastRound() {
        return isLastRound;
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
        playerTurnChecker(player);

        Development development = market.findMatchingDevelopmentOverAllLevels(developmentName);
        player.buyDevelopment(development, payment);
        market.removeVisibleDevelopment(development);
        market.refillMarket(development.level());
        tokenBank.addTokens(payment);

        history.log(new ActionReport(currentPlayer.getName(), "bought development " + development + " for " + payment));
        endPhaseOfTurn(true);
    }

    private void endPhaseOfTurn(boolean tokenOverflowShouldBeChecked) {

        if (currentPlayer.hasTooManyTokens() && tokenOverflowShouldBeChecked) {
            history.log(new ActionReport(currentPlayer.getName(), "has too many tokens. Waiting for player to return tokens..."));
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
        playerTurnChecker(player);

        Development development = market.findMatchingDevelopmentOverAllLevels(developmentName);
        player.reserveDevelopment(development);
        market.removeVisibleDevelopment(development);
        market.refillMarket(development.level());

        givePlayerGoldTokenIfPossible(player);

        history.log(new ActionReport(currentPlayer.getName(), "reserved development " + development));
        endPhaseOfTurn(true);

    }

    public void buyReservedDevelopment(Purse payment, String developmentName, Player player){
        checkIfActionCanBeCarriedOut(GameState.TURN_ACTION);
        playerTurnChecker(player);

        Development development = player.findDevelopmentInReservedDevelopments(developmentName);
        player.buyDevelopment(development, payment);
        player.removeReservedDevelopment(development);

        tokenBank.addTokens(payment);

        history.log(new ActionReport(currentPlayer.getName(), "bought reserved development " + development + " for " + payment));
        endPhaseOfTurn(false);

    }

    private void givePlayerGoldTokenIfPossible(Player player) {
        if (tokenBank.getTokens().get(Token.GOLD) > 0) {
            tokenBank.removeToken(Token.GOLD, 1);
            player.getTokens().addToken(Token.GOLD, 1);
        }
    }

    public void reserveDevelopmentFromLevel(int level, Player player){
        checkIfActionCanBeCarriedOut(GameState.TURN_ACTION);
        playerTurnChecker(player);

        player.reserveDevelopment(market.takeTopDevelopment(level));
        givePlayerGoldTokenIfPossible(player);

        history.log(new ActionReport(currentPlayer.getName(), "reserved the top development from level " + level + " stack"));
        endPhaseOfTurn(true);

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
            history.log(new ActionReport(currentPlayer.getName(), "needs to choose a noble..."));
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

    public void acquireNoble(Noble noble) {
        currentPlayer.claimNoble(noble);
        unclaimedNobles.remove(noble);
        history.log(new ActionReport(currentPlayer.getName(), "got visited by noble '" + noble.name() + "'"));
    }

    public void acquireTokens(Player player, Purse tokens){
        checkIfActionCanBeCarriedOut(GameState.TURN_ACTION);
        playerTurnChecker(player);

        int sizeOfTokensToAcquire = tokens.getAvailableTokens().size();
        tokenBankRuleCheck(tokens);

        player.acquireTokens(tokens, sizeOfTokensToAcquire);
        tokenBank.removeTokens(tokens);

        history.log(new ActionReport(currentPlayer.getName(), "took " + tokens));
        endPhaseOfTurn(true);

    }

    private void tokenBankRuleCheck(Purse tokensToAcquire){
        for (Map.Entry<Token, Integer> tokenToAdd : tokensToAcquire.getTokens().entrySet()) {
            if (tokenToAdd.getValue() == MAX_OF_SAME_TOKEN && tokenBank.getTokenValue(tokenToAdd.getKey()) < MIN_BANK_VALUE_FOR_TWO_OF_SAME_TOKENS) {
                throw new SplendorGameRuleException("You can only take two of the same token type if the bank has more than four available tokens");
            }
        }
    }

    public void playerTurnChecker(Player player) {
        if (!player.equals(currentPlayer)) {
            throw new SplendorGameRuleException("It's not this player's turn");
        }
    }

    public void returnTokens(Player player, Purse tokensToReturn) {
        checkIfActionCanBeCarriedOut(GameState.RETURN_GEMS);
        playerTurnChecker(player);

        player.returnTokens(tokensToReturn);
        tokenBank.addTokens(tokensToReturn);

        history.log(new ActionReport(currentPlayer.getName(), "returned " + tokensToReturn + " to the token bank"));
        endPhaseOfTurn(false);
    }

    public List<Noble> setUnclaimedNobles() {
        NobleReader nobleReader = new NobleReader();
        return nobleReader.getRandomNobles(getTotalPlayers());
    }

    public GameState getGameState() {
        return gameState;
    }

    public List<Noble> getUnclaimedNobles() {
        return unclaimedNobles;
    }

    public Purse getTokenBank() {
        return tokenBank;
    }

    public Logger getHistory() {
        return history;
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
