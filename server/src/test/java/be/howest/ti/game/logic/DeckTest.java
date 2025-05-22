package be.howest.ti.game.logic;

import be.howest.ti.game.logic.exceptions.SplendorGameResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    private Deck l1;

    @BeforeEach
    void setUp() {
        List<Development> developments = List.of(
                new Development("Developement 1", 1, 1, null, null),
                new Development("Developement 2", 1, 2, null, null),
                new Development("Developement 3", 1, 3, null, null),
                new Development("Developement 4", 1, 4, null, null),
                new Development("Developement 5", 1, 5, null, null),
                new Development("Developement 6", 1, 6, null, null)
        );

        this.l1 = new Deck(developments, 1);
    }

    @Test
    void removeVisibleDevelopment() {
        Development firstVisible = l1.getVisibleDevelopments().getFirst();
        l1.removeVisibleDevelopment(firstVisible);
        assertFalse(l1.getVisibleDevelopments().contains(firstVisible));
        assertThrows(SplendorGameResourceNotFoundException.class, () -> l1.removeVisibleDevelopment(new Development("Developement 7", 1, 6, null, null)));
    }

    @Test
    void getTotalInvisible() {
        assertEquals(2, l1.getTotalInvisible());
        Deck l2 = new Deck(List.of(), 1);
        assertThrows(SplendorGameResourceNotFoundException.class, l2::takeTopDevelopment);
    }

    @Test
    void testRemoveVisibleDevelopmentString() {
        Development firstVisible = l1.getVisibleDevelopments().getFirst();
        l1.removeVisibleDevelopment(firstVisible);
        assertFalse(l1.getVisibleDevelopments().contains(firstVisible));
        assertThrows(SplendorGameResourceNotFoundException.class, () -> l1.removeVisibleDevelopment(new Development("Developement 7", 1, 6, null, null)));

    }
}