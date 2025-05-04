package be.howest.ti.game.logic.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SplendorServiceImplTest {

    private SplendorServiceImpl service;

    @BeforeEach
    public void init() {
        service = new SplendorServiceImpl();
    }

    @Test
    public void createLobbyWithTwoParameters() {
        service.createLobby(2, "Yoni");

        assertNull(service.getGames().getFirst().getGameName());
        assertEquals(2, service.getGames().getFirst().getMaxPlayers());
        assertEquals("Yoni", service.getGames().getFirst().getPlayers().getFirst().getName());
    }

    @Test
    public void createLobbyWithThreeParameters() {
        service.createLobby(4, "John", "Epic Splendor Game");

        assertEquals("Epic Splendor Game", service.getGames().getFirst().getGameName());
        assertEquals(4, service.getGames().getFirst().getMaxPlayers());
        assertEquals("John", service.getGames().getFirst().getPlayers().getFirst().getName());
    }

    @Test
    public void startGameWhenLobbyIsFull() {
        service.createLobby(2, "Yoni");
        System.out.println(service.getGames().getFirst().getGameId());

        service.startGame(0);

        System.out.println(service.getGames().getFirst().getGameId());
    }

}