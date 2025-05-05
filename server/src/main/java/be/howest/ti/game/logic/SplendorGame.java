package be.howest.ti.game.logic;

import java.util.List;
import java.util.Set;

public class SplendorGame extends GameSuperclass {
    private Purse tokenBank; // TODO make final
    private Set<Noble> unclaimedNobles; // TODO make final
    private final Market market;
    private Player currentPlayer;
    private GameState gameState;
    private Player winner;

    public SplendorGame(GameSuperclass gameLobby){
        super(gameLobby);
        this.market = new Market();
        this.currentPlayer = gameLobby.getPlayers().getFirst();
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

    public void acquireTokens(Purse tokens){
        //TODO
    }

    public void returnTokens(Purse tokens){
        //TODO
    }





}
