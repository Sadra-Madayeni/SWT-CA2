package airplane.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NavigationSystemTest {

    @Test
    void testSingleArgConstructor_ShouldSetDestinationAndDefaultRouteToTrue() {
        NavigationSystem navSystem = new NavigationSystem("Tehran");
        
        assertEquals("Tehran", navSystem.getDestination(), "Destination should match the provided argument.");
        assertTrue(navSystem.isRouteAvailable(), "Route should be available by default.");
    }

    @Test
    void testTwoArgConstructor_ShouldSetDestinationAndRouteStatus() {
        NavigationSystem navSystem = new NavigationSystem("London", false);
        
        assertEquals("London", navSystem.getDestination(), "Destination should match the provided argument.");
        assertFalse(navSystem.isRouteAvailable(), "Route availability should match the provided boolean.");
    }

    @Test
    void testConstructor_WithNullDestination_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new NavigationSystem(null),
            "Should throw exception when destination is null."
        );
        assertEquals("Destination must not be blank.", exception.getMessage());
    }

    @Test
    void testConstructor_WithBlankDestination_ShouldThrowException() {
        IllegalArgumentException exception1 = assertThrows(
            IllegalArgumentException.class,
            () -> new NavigationSystem(""),
            "Should throw exception when destination is empty."
        );
        assertEquals("Destination must not be blank.", exception1.getMessage());

        IllegalArgumentException exception2 = assertThrows(
            IllegalArgumentException.class,
            () -> new NavigationSystem("   "),
            "Should throw exception when destination contains only spaces."
        );
        assertEquals("Destination must not be blank.", exception2.getMessage());
    }

    @Test
    void testSetRouteAvailable_ShouldUpdateRouteStatus() {
        NavigationSystem navSystem = new NavigationSystem("Paris", true);
        
        navSystem.setRouteAvailable(false);
        assertFalse(navSystem.isRouteAvailable(), "Route should not be available after setting to false.");
        
        navSystem.setRouteAvailable(true);
        assertTrue(navSystem.isRouteAvailable(), "Route should be available after setting to true.");
    }
}