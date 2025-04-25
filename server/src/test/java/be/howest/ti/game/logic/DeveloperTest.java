package be.howest.ti.game.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeveloperTest {

    @Test
    void testConstructorWithValidParameters() {
        Developer developer = new Developer("John", "Doe");
        assertEquals("John Doe", developer.getFullName());
    }

    @Test
    void testConstructorWithNullFirstName() {
        assertThrows(IllegalArgumentException.class, () -> new Developer(null, "Doe"));
    }

    @Test
    void testConstructorWithEmtyLastName() {
        assertThrows(IllegalArgumentException.class, () -> new Developer("John", ""));
    }

}