package be.howest.ti.game.logic;

public class PrivateSplendorGame extends SplendorGame{
    private final String password;

    public PrivateSplendorGame(GameSuperclass gameLobby, String password) {
        super(gameLobby);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
