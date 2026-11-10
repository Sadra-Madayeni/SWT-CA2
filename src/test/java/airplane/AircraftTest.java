package airplane;

import airplane.components.Engine;
import airplane.components.FuelSystem;
import airplane.components.LandingGear;
import airplane.components.NavigationSystem;
import airplane.exceptions.FlightException;
import airplane.models.FlightStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AircraftTest {

    private Engine mockEngine;
    private FuelSystem mockFuelSystem;
    private LandingGear mockLandingGear;
    private NavigationSystem mockNavigationSystem;
    private Aircraft aircraft;

    @BeforeEach
    void setUp() {
        
        mockEngine = mock(Engine.class);
        mockFuelSystem = mock(FuelSystem.class);
        mockLandingGear = mock(LandingGear.class);
        mockNavigationSystem = mock(NavigationSystem.class);

        aircraft = new Aircraft(mockEngine, mockFuelSystem, mockLandingGear, mockNavigationSystem);
    }

    @Test
    void testPrepareForTakeoff_WhenAllSystemsGo_ShouldChangeStatusToReady() {
    
        when(mockEngine.isOperational()).thenReturn(true);
        when(mockFuelSystem.hasEnoughFuel()).thenReturn(true);
        when(mockNavigationSystem.isRouteAvailable()).thenReturn(true);

        aircraft.prepareForTakeoff();

        assertEquals(FlightStatus.READY, aircraft.getStatus());
    
        verify(mockEngine, times(1)).isOperational();
        verify(mockFuelSystem, times(1)).hasEnoughFuel();
    }
 
    @Test
    void testPrepareForTakeoff_WhenEngineNotOperational_ShouldThrowException() {
 
        when(mockEngine.isOperational()).thenReturn(false);

        FlightException exception = assertThrows(
                FlightException.class,
                () -> aircraft.prepareForTakeoff()
        );

        assertEquals("Cannot prepare for takeoff: engine is not operational.", exception.getMessage());
        assertEquals(FlightStatus.GROUNDED, aircraft.getStatus());
 
        verify(mockFuelSystem, never()).hasEnoughFuel();
    }
 
    @Test
    void testTakeOff_WhenReady_ShouldStartEngineAndRetractGear() {
   
        when(mockEngine.isOperational()).thenReturn(true);
        when(mockFuelSystem.hasEnoughFuel()).thenReturn(true);
        when(mockNavigationSystem.isRouteAvailable()).thenReturn(true);

        aircraft.prepareForTakeoff();
 
        aircraft.takeOff();
 
        verify(mockEngine, times(1)).start();
        verify(mockLandingGear, times(1)).retract();

        assertEquals(FlightStatus.IN_FLIGHT, aircraft.getStatus());
    }
 
    @Test
    void testTakeOff_WhenEngineStartFails_ShouldAbortTakeOff() {
 
        when(mockEngine.isOperational()).thenReturn(true);
        when(mockFuelSystem.hasEnoughFuel()).thenReturn(true);
        when(mockNavigationSystem.isRouteAvailable()).thenReturn(true);

        aircraft.prepareForTakeoff();
 
        doThrow(new IllegalStateException("Engine fail")).when(mockEngine).start();

        FlightException exception = assertThrows(
                FlightException.class,
                () -> aircraft.takeOff()
        );

        assertEquals("Takeoff failed.", exception.getMessage());
        assertEquals(FlightStatus.ABORTED, aircraft.getStatus());
    }
 
    @Test
    void testCruise_WhenFuelSystemThrowsException_ShouldEmergencyLandAndThrow() {

        when(mockEngine.isOperational()).thenReturn(true);
        when(mockFuelSystem.hasEnoughFuel()).thenReturn(true);
        when(mockNavigationSystem.isRouteAvailable()).thenReturn(true);

        aircraft.prepareForTakeoff();
        aircraft.takeOff();
 
        reset(mockEngine); 
 
        when(mockEngine.isOperational()).thenReturn(true);
 
        doThrow(new IllegalStateException("Empty Tank")).when(mockFuelSystem).consumeFuel(any(Double.class));

        assertThrows(
                FlightException.class,
                () -> aircraft.cruise(50.0)
        );
 
        verify(mockLandingGear, times(1)).deploy();
        verify(mockEngine, times(1)).stop();
        
        assertEquals(FlightStatus.LANDED, aircraft.getStatus());
    }

    @Test
    void testLand_WhenInFlight_ShouldDeployGearAndStopEngine() {

        when(mockEngine.isOperational()).thenReturn(true);
        when(mockFuelSystem.hasEnoughFuel()).thenReturn(true);

        when(mockNavigationSystem.isRouteAvailable()).thenReturn(true);
        aircraft.prepareForTakeoff();
        aircraft.takeOff();
 
        doNothing().when(mockLandingGear).deploy();
        doNothing().when(mockEngine).stop();

        aircraft.land();
 
        verify(mockLandingGear, times(1)).deploy();
        verify(mockEngine, times(1)).stop();

        assertEquals(FlightStatus.LANDED, aircraft.getStatus());
    }
}