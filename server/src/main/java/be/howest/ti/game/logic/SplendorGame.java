package be.howest.ti.game.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SplendorGame extends GameSuperclass {
    private Purse tokenBank; // TODO make final
    private Set<Noble> unclaimedNobles; // TODO make final
    private final Market market;
    private Player currentPlayer;
    private GameState gameState;
    private Player winner;

    private static final int ONE_NOBLE = 1;

    public SplendorGame(GameSuperclass gameLobby){
        super(gameLobby);
        this.market = new Market();
        this.currentPlayer = getPlayers().getFirst();
    }

    public Player getWinner() {
        return winner;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Market getMarket() {
        return market;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void buyDevelopment(Purse payment, String developmentName){
        currentPlayer.checkIfPaymentIsSufficient(market.findMatchingDevelopmentOverAllLevels(developmentName), payment);
        currentPlayer.buyDevelopment(market.removeVisibleDevelopment(developmentName), payment);
    }

    public void reserveDevelopment(String developmentName){
        currentPlayer.checkIfPlayerIsAllowedToReserve();
        currentPlayer.reserveDevelopment(market.removeVisibleDevelopment(developmentName));
    }

    public void reserveDevelopmentFromLevel(int level){
        currentPlayer.checkIfPlayerIsAllowedToReserve();
        currentPlayer.reserveDevelopment(market.takeTopDevelopment(level));
    }

    public void checkForNoble(){
        List<Noble> possibleNobles = new ArrayList<>();
        for (Noble noble : unclaimedNobles){
            if (playerMeetsRequirements(currentPlayer, noble)){
                possibleNobles.add(noble);

            }

        }
        if (possibleNobles.isEmpty()){
            return;
        }
        chooseNobleNecessaryCheck(possibleNobles);
    }

    private void chooseNobleNecessaryCheck(List<Noble> possibleNobles){
        if (possibleNobles.size() > ONE_NOBLE){
            setGameState(GameState.CHOOSE_NOBLE);
            //TODO : ADD FUNCTIONALITY FOR WHEN TWO OR MORE NOBLES CLAIMABLE --> WHEN DOING ENDPOINT NOBLES
        } else {
            acquireNoble(possibleNobles);
        }
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

    public void acquireNoble(List<Noble> possibleNobles){
        currentPlayer.claimNoble(possibleNobles.getFirst());
    }

    public void acquireTokens(Player player, Purse tokens){
        playerTurnChecker(player);
        player.acquireTokens(tokens.getTokens());

        tokenBank.removeTokens(tokens.getTokens());
    }

    private void playerTurnChecker(Player player) {
        if(!player.equals(currentPlayer)){
            throw new IllegalStateException("It's not this player's turn");
        }
    }

    public void returnTokens(Purse tokens){
        //TODO
    }

    //For testing purposes
    public void setUnclaimedNobles(Set<Noble> nobles) {
        this.unclaimedNobles = nobles;
    }


    //For testing purposes
    public void setTokenBank(Purse tokensToSetTokenBank){
        this.tokenBank = tokensToSetTokenBank;
    }


    public GameState getGameState() {
        return gameState;
    }
}
