package be.howest.ti.game.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SplendorGame {
    private int gameId; // TODO make final
    private String gameName; // TODO make final
    private List<Player> players; // TODO make final
    private Purse tokenBank; // TODO make final
    private Set<Noble> unclaimedNobles; // TODO make final
    private Market market; // TODO make final
    private Player currentPlayer;
    private GameState gameState;
    private Player winner;

    public SplendorGame(GameLobby gameLobby){
        //TODO
    }

    public int getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
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
        chooseNobleNecessaryCheck(possibleNobles);
    }

    private void chooseNobleNecessaryCheck(List<Noble> possibleNobles) {
        int ONE_NOBLE = 1;
        if (possibleNobles.size() > ONE_NOBLE){
            setGameState(GameState.CHOOSE_NOBLE);
        } else {
            currentPlayer.claimNoble(possibleNobles.getFirst());
        }
    }

    private boolean playerMeetsRequirements(Player player, Noble noble) {
        for (Token token : Token.values()) {
            int required = noble.neededBonuses().getTokens().get(token);
            int actual = player.getBonuses().getTokens().get(token);
            if (actual < required) {
                return false;
            }
        }
        return true;
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
