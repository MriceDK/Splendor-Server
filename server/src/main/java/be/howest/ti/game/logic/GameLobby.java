package be.howest.ti.game.logic;


import java.util.ArrayList;
import java.util.List;

public class GameLobby {

    private int gameId; // TODO make final
    private List<Player> players;
    private int maxPlayers; // TODO make final
    private String gameName;

    public GameLobby(String gameName, int maxPlayers){
        this.gameName = gameName;
        this.maxPlayers = maxPlayers;
        players = new ArrayList<>();
    }

    public GameLobby(int maxPlayers){
        //TODO
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getGameName() {
        return gameName;
    }

    private void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void startGame(){
        //TODO
    }

    public void addPlayer(String name){
        Player newPlayer = new Player(name);

        if (players.contains(newPlayer)) {
            throw new IllegalStateException("There already exists a player with the same name in this game.");
        }

        players.add(newPlayer);
    }



}