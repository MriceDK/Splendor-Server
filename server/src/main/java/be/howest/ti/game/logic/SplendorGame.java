package be.howest.ti.game.logic;

import java.util.List;

public class SplendorGame {
    private final int gameId;
    private final String gameName;
    private final List<Player> players;
    private final Purse tokenBank;
    private final Set<Noble> unclaimedNobles;
    private final Market market;
    private Player currentPlayer;
    private GameState gameState;
    private Player winner;


}
