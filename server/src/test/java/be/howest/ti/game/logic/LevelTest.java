package be.howest.ti.game.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {

    private Level l1;
    private List<Development> developments;

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

        this.developments = developments;
        this.l1 = new Level(developments, 1);
    }


    @Test
    void getVisibleDevelopments() {
        assertEquals(developments.subList(0, 4), l1.getVisibleDevelopments());
    }

    @Test
    void removeVisibleDevelopment() {
        l1.removeVisibleDevelopment(developments.getFirst());
        assertEquals(developments.subList(1, 5), l1.getVisibleDevelopments());
        assertThrows(IllegalArgumentException.class, () -> l1.removeVisibleDevelopment(new Development("Developement 7", 1, 6, null, null)));
    }

    @Test
    void getTotalInvisible() {
        assertEquals(2, l1.getTotalInvisible());
        Level l2 = new Level(List.of(), 1);
        assertThrows(IllegalStateException.class, l2::takeTopDevelopment);
    }

    @Test
    void takeTopDevelopment() {
        assertEquals(developments.get(4), l1.takeTopDevelopment());
    }
}