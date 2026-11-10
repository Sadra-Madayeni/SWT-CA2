package airplane.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LandingGearTest {

    @Test
    void testInitialState_ShouldBeDeployed() {
        LandingGear landingGear = new LandingGear();
        assertTrue(landingGear.isDeployed(), "Landing gear should be deployed by default.");
    }

    @Test
    void testRetract_ShouldSetDeployedToFalse() {
        LandingGear landingGear = new LandingGear();
        
        landingGear.retract();
        
        assertFalse(landingGear.isDeployed(), "Landing gear should not be deployed after retract() is called.");
    }

    @Test
    void testDeploy_AfterRetract_ShouldSetDeployedToTrue() {
        LandingGear landingGear = new LandingGear();
        landingGear.retract();  
        
        landingGear.deploy();  
        
        assertTrue(landingGear.isDeployed(), "Landing gear should be deployed after deploy() is called.");
    }

    @Test
    void testDeploy_WhenAlreadyDeployed_ShouldRemainDeployed() {
        LandingGear landingGear = new LandingGear();
        landingGear.deploy(); 
        
        assertTrue(landingGear.isDeployed(), "Landing gear should remain deployed if deploy() is called again.");
    }
}