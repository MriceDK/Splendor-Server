package be.howest.ti.game.logic;


public class GameLobby extends GameSuperclass {

    public GameLobby(int gameId, String gameName, int maxPlayers, boolean privateGame) {
        super(gameId, gameName, maxPlayers, privateGame);
    }

    public GameLobby(int gameId, int maxPlayers, boolean privateGame) {
        this(gameId, null, maxPlayers, privateGame);
    }

    public SplendorGame startGame() {
        return new SplendorGame(this);
    }

    public boolean isFull() {
        return super.getMaxPlayers() == super.getTotalPlayers();
    }
}