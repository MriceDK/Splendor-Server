package be.howest.ti.game.logic;

public class PrivateGameLobby extends GameLobby{
    private final String password;

    public PrivateGameLobby(int gameId, String gameName, int maxPlayers, String password) {
        super(gameId, gameName, maxPlayers);
        this.password = password;
    }

    public PrivateGameLobby(int gameId, int maxPlayers, String password) {
        this(gameId, null, maxPlayers, password);
    }

    public String getPassword() {
        return password;
    }
}
