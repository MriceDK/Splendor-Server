package be.howest.ti.game.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SplendorGame extends GameSuperclass {

    private final static int TOKEN_AMOUNT_IN_TOKENBANK_FOR_FOUR_PLAYERS = 7;
    private final static int TOKEN_AMOUNT_IN_TOKENBANK_FOR_THREE_PLAYERS = 5;
    private final static int TOKEN_AMOUNT_IN_TOKENBANK_FOR_TWO_PLAYERS = 4;

    private final static int INITAL_GOLD_AMOUNT = 5;

    private Purse tokenBank; // TODO make final
    private Set<Noble> unclaimedNobles; // TODO make final
    private final Market market;
    private Player currentPlayer;
    private GameState gameState;
    private Player winner;

    private static final int ONE_NOBLE = 1;

    public SplendorGame(GameSuperclass gameLobby){
        super(gameLobby);
        // TODO Remove this dummy data when we have the actual nobles
        this.unclaimedNobles = Set.of(
                new Noble("noble-1", 3, new Purse(
                        Map.of(
                                Token.SAPPHIRE, 4,
                                Token.RUBY, 4
                        )
                )),
                new Noble("noble-2", 3, new Purse(
                        Map.of(
                                Token.ONYX, 4,
                                Token.EMERALD, 4
                        )
                ))
        );

        this.market = new Market();
        this.currentPlayer = getPlayers().getFirst();
        this.tokenBank = generateTokenBank();
    }

    public Player getWinner() {
        return null; // TODO change this method into a calculation method, that calculates if there is a winner or not
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

    public Development reserveDevelopment(String developmentName){
        currentPlayer.checkIfPlayerIsAllowedToReserve();
        return currentPlayer.reserveDevelopment(market.removeVisibleDevelopment(developmentName));
    }

    public Development reserveDevelopmentFromLevel(int level){
        currentPlayer.checkIfPlayerIsAllowedToReserve();
        return currentPlayer.reserveDevelopment(market.takeTopDevelopment(level));
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

    public void returnTokens(Purse tokensToReturn) {
        currentPlayer.ReturnTokens(tokensToReturn.getTokens());
        tokenBank.addTokens(tokensToReturn.getTokens());
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
        }
        else if (getTotalPlayers() == 3) {
            amountPerToken = TOKEN_AMOUNT_IN_TOKENBANK_FOR_THREE_PLAYERS;
        }
        else if (getTotalPlayers() == 2) {
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
                        Token.GOLD, INITAL_GOLD_AMOUNT
                )
        );
    }
}
