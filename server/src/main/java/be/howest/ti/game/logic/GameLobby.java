package be.howest.ti.game.logic;


import java.util.List;

public class GameLobby extends GameSuperclass {

    public GameLobby(int gameId, String gameName, int maxPlayers){
        super(gameId, gameName, maxPlayers);
    }

    public GameLobby(int gameId, int maxPlayers){
        this(gameId, null, maxPlayers);
    }

    public SplendorGame startGame(){
        if (isFull()) {
            return new SplendorGame(this);
        } else {
            return null;
        }

    }
    public void addPlayer(String name){
        Player newPlayer = new Player(name);

        validateNewPlayer(newPlayer);

        players.add(newPlayer);

    }

    private void validateNewPlayer(Player newPlayer) {
        if (players.size() >= maxPlayers){
            throw new IllegalStateException("There are already " + maxPlayers + " in the game!");

        }

        if (players.contains(newPlayer)) {
            throw new IllegalStateException("There already exists a player with the same name in this game.");
        }

    private boolean isFull() {
        return super.getMaxPlayers() == super.getTotalPlayers();
    }

}