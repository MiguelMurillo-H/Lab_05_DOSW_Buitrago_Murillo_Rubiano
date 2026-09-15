package edu.eci.dosw.tdd.skyrescue.center;

import edu.eci.dosw.tdd.skyrescue.drone.Drone;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RescueCenterTest {

    @Test
    void shouldRegisterDroneWhenDataIsValid() {
        RescueCenter center = new RescueCenter();
        Drone drone = new Drone("D-01", "Phantom X", 15);
        
        boolean result = center.addDrone(drone);
        
        assertTrue(result);
    }
}
