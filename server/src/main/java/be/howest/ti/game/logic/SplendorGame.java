package be.howest.ti.game.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SplendorGame extends GameSuperclass {
    private Purse tokenBank; // TODO make final
    private Set<Noble> unclaimedNobles; // TODO make final
    private Market market; // TODO make final
    private Player currentPlayer;
    private GameState gameState;
    private Player winner;

    private static final int ONE_NOBLE = 1;
    private static final int EMPTY_NOBLES = 0;

    public SplendorGame(GameSuperclass gameLobby){
        super(gameLobby);
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

    public void buyDevelopment(Purse payment, Development development){
        //TODO
    }

    public void reserveDevelopment(Development development){
        //TODO
    }

    public void checkForNoble(){
        List<Noble> possibleNobles = new ArrayList<>();
        for (Noble noble : unclaimedNobles){
            if (playerMeetsRequirements(currentPlayer, noble)){
                possibleNobles.add(noble);

            }

        }
        if (possibleNobles.size() == EMPTY_NOBLES){
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

    public void acquireTokens(Purse tokens){
        //TODO
    }

    public void returnTokens(Purse tokens){
        //TODO
    }

    //For testing purposes
    public void setUnclaimedNobles(Set<Noble> nobles) {
        this.unclaimedNobles = nobles;
    }





}
