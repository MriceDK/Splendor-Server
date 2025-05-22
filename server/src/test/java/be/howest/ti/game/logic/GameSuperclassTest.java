package be.howest.ti.game.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameSuperclassTest {

    @Test
    void testNaturalOrderByGameIdAscending() {
        List<GameSuperclass> games = new ArrayList<>();

        GameLobby gameLobby = new GameLobby(2, 2);
        gameLobby.addPlayer("bob");

        games.add(new GameLobby(1, 1));
        games.add(new GameLobby(4, 4));
        games.add(new GameLobby(3, 3));
        games.add(new GameLobby(5, 1));
        games.add(new SplendorGame(gameLobby));

        Collections.sort(games);

        assertEquals(1, games.getFirst().getGameId());
        assertEquals(2, games.get(1).getGameId());
        assertEquals(3, games.get(2).getGameId());
        assertEquals(4, games.get(3).getGameId());
        assertEquals(5, games.getLast().getGameId());

    }

}