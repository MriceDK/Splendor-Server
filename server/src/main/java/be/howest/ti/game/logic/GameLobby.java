package be.howest.ti.game.logic;


public class GameLobby extends GameSuperclass {
    private final String password;

    public GameLobby(int gameId, String gameName, int maxPlayers, boolean privateGame, String password) {
        super(gameId, gameName, maxPlayers, privateGame);
        if (privateGame){
            this.password = password;
        } else {
            this.password = null;
        }
    }

    public GameLobby(int gameId, int maxPlayers, boolean privateGame, String password) {
        this(gameId, null, maxPlayers, privateGame);
        if (privateGame){
            this.password = password;
        } else {
            this.password = null;
        }
    }

    public SplendorGame startGame() {
        return new SplendorGame(this);
    }

    public boolean isFull() {
        return super.getMaxPlayers() == super.getTotalPlayers();
    }
}