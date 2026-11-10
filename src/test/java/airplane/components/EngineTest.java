package airplane.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EngineTest {

    @Test
    void testDefaultConstructor_ShouldBeOperationalAndNotRunning() {
        Engine engine = new Engine();
        assertTrue(engine.isOperational(), "Engine should be operational by default.");
        assertFalse(engine.isRunning(), "Engine should not be running initially.");
    }

    @Test
    void testParameterizedConstructor_ShouldSetOperationalStatus() {
        Engine engine = new Engine(false);
        assertFalse(engine.isOperational(), "Engine should not be operational based on constructor argument.");
        assertFalse(engine.isRunning(), "Engine should not be running initially.");
    }

    @Test
    void testStart_WhenOperational_ShouldStartEngine() {
        Engine engine = new Engine();
        engine.start();
        assertTrue(engine.isRunning(), "Engine should be running after start() is called.");
    }

    @Test
    void testStart_WhenNotOperational_ShouldThrowException() {
        Engine engine = new Engine(false);
        
        IllegalStateException exception = assertThrows(
            IllegalStateException.class, 
            engine::start,
            "Should throw IllegalStateException when starting a non-operational engine."
        );
        
        assertEquals("The engine is not operational.", exception.getMessage());
    }

    @Test
    void testStop_ShouldStopRunningEngine() {
        Engine engine = new Engine();
        engine.start();  
        
        engine.stop();  
        
        assertFalse(engine.isRunning(), "Engine should not be running after stop() is called.");
    }

    @Test
    void testFail_ShouldMakeEngineNotOperationalAndNotRunning() {
        Engine engine = new Engine();
        engine.start();  
        
        engine.fail();  
        
        assertFalse(engine.isOperational(), "Engine should not be operational after failure.");
        assertFalse(engine.isRunning(), "Engine should stop running after failure.");
    }
}