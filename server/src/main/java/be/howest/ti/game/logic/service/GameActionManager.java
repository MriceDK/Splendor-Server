package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.Noble;
import be.howest.ti.game.logic.Player;
import be.howest.ti.game.logic.Purse;
import be.howest.ti.game.logic.SplendorGame;

public class GameActionManager {

    private final GameLobbyManager lobbyManager;

    public GameActionManager(GameLobbyManager lobbyManager){
        this.lobbyManager = lobbyManager;
    }



    public Player buyReservedDevelopment(int gameId, String playerName, String developmentName, Purse payment) {
        SplendorGame game = lobbyManager.findStartedGame(gameId);
        Player player = game.findPlayer(playerName);
        game.buyReservedDevelopment(payment, developmentName, player);

        return player;
    }


    public Player updateTokens(boolean takeOrReturn, int gameId, String playerName, Purse tokensToChange) {
        SplendorGame game = lobbyManager.findStartedGame(gameId);
        Player player = game.findPlayer(playerName);

        if (takeOrReturn) {
            game.acquireTokens(player, tokensToChange);
        } else {
            game.returnTokens(player, tokensToChange);
        }

        return player;

    }


    public Player buyDevelopment(int gameId, String playerName, String developmentName, Purse payment) {
        SplendorGame game = lobbyManager.findStartedGame(gameId);
        Player player = game.findPlayer(playerName);
        game.buyDevelopment(payment, developmentName, player);

        return player;
    }


    public Player reserveDevelopment(int gameId, String playerName, String name) {
        SplendorGame game = lobbyManager.findStartedGame(gameId);
        Player player = game.findPlayer(playerName);
        game.reserveDevelopment(name, player);
        return player;
    }


    public Player reserveDevelopmentFromLevel(int gameId, String playerName, int level) {
        SplendorGame game = lobbyManager.findStartedGame(gameId);
        Player player = game.findPlayer(playerName);
        game.reserveDevelopmentFromLevel(level, player);
        return player;
    }


    public Noble chooseNoble(int gameId, String playerName, Noble noble) {
        SplendorGame game = lobbyManager.findStartedGame(gameId);
        Player player = game.findPlayer(playerName);

        return game.chooseNoble(player, noble);
    }

    public Player getCurrentPlayer(int gameId) {
        SplendorGame game = lobbyManager.findStartedGame(gameId);

        return game.getCurrentPlayer();
    }
}
