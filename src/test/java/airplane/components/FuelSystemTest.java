package airplane.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FuelSystemTest {

    @Test
    void testConstructor_WithValidValues_ShouldInitializeCorrectly() {
        FuelSystem fuelSystem = new FuelSystem(100.0, 50.0);
        assertEquals(100.0, fuelSystem.getRemainingFuel(), "Initial fuel should match the constructor argument.");
    }

    @Test
    void testConstructor_WithNegativeInitialFuel_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new FuelSystem(-10.0, 50.0),
            "Should throw exception when initial fuel is negative."
        );
        assertEquals("Fuel values cannot be negative.", exception.getMessage());
    }

    @Test
    void testConstructor_WithNegativeMinimumTakeoffFuel_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new FuelSystem(100.0, -50.0),
            "Should throw exception when minimum takeoff fuel is negative."
        );
        assertEquals("Fuel values cannot be negative.", exception.getMessage());
    }

    @Test
    void testHasEnoughFuel_WhenFuelIsSufficient_ShouldReturnTrue() {
        FuelSystem fuelSystem = new FuelSystem(100.0, 50.0);
        assertTrue(fuelSystem.hasEnoughFuel(), "Should return true when remaining fuel is greater than minimum.");
        
        FuelSystem exactFuelSystem = new FuelSystem(50.0, 50.0);
        assertTrue(exactFuelSystem.hasEnoughFuel(), "Should return true when remaining fuel equals minimum.");
    }

    @Test
    void testHasEnoughFuel_WhenFuelIsInsufficient_ShouldReturnFalse() {
        FuelSystem fuelSystem = new FuelSystem(30.0, 50.0);
        assertFalse(fuelSystem.hasEnoughFuel(), "Should return false when remaining fuel is less than minimum.");
    }

    @Test
    void testConsumeFuel_WithValidAmount_ShouldDecreaseFuel() {
        FuelSystem fuelSystem = new FuelSystem(100.0, 50.0);
        fuelSystem.consumeFuel(30.0);
        assertEquals(70.0, fuelSystem.getRemainingFuel(), "Remaining fuel should be decreased by the consumed amount.");
    }

    @Test
    void testConsumeFuel_WithZeroOrNegativeAmount_ShouldThrowException() {
        FuelSystem fuelSystem = new FuelSystem(100.0, 50.0);
        
        assertThrows(IllegalArgumentException.class, () -> fuelSystem.consumeFuel(0), "Should throw exception when consumed amount is zero.");
        assertThrows(IllegalArgumentException.class, () -> fuelSystem.consumeFuel(-20.0), "Should throw exception when consumed amount is negative.");
    }

    @Test
    void testConsumeFuel_WhenAmountExceedsRemainingFuel_ShouldThrowException() {
        FuelSystem fuelSystem = new FuelSystem(50.0, 50.0);
        
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> fuelSystem.consumeFuel(60.0),
            "Should throw exception when trying to consume more fuel than available."
        );
        assertEquals("Not enough fuel.", exception.getMessage());
    }
}