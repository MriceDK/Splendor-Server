package be.howest.ti.game.logic;

import java.util.List;
import java.util.Set;

public class SplendorGame extends GameSuperclass {
    private Purse tokenBank; // TODO make final
    private Set<Noble> unclaimedNobles; // TODO make final
    private Market market; // TODO make final
    private Player currentPlayer;
    private GameState gameState;
    private Player winner;

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

    public boolean checkForNoble(){
        //TODO
        return false;
    }

    public void acquireNoble(){
        //TODO
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





}
