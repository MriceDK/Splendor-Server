package be.howest.ti.game.logic.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SplendorServiceImplTest {

    @Test
    public void createLobby() {
        SplendorServiceImpl service = new SplendorServiceImpl();

        service.createLobby(2, "Yoni");

        System.out.println(service.getGames());
        assertNull(service.getGames().getFirst().getGameName());
        assertEquals(2, service.getGames().getFirst().getMaxPlayers());
        assertEquals("Yoni", service.getGames().getFirst().getPlayers().getFirst().getName());
    }

}