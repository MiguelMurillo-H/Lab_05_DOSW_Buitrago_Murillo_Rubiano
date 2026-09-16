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

    @Test
    void shouldNotRegisterDroneWhenDroneIsNull() {
        RescueCenter center = new RescueCenter();

        boolean result = center.addDrone(null);

        assertFalse(result);
    }

    @Test
    void shouldNotRegisterDroneWhenIdIsNull() {
        RescueCenter center = new RescueCenter();
        Drone drone = new Drone(null, "Phantom X", 15);

        boolean result = center.addDrone(drone);

        assertFalse(result);
    }

    @Test
    void shouldNotRegisterDroneWhenIdIsBlank() {
        RescueCenter center = new RescueCenter();
        Drone drone = new Drone("   ", "Phantom X", 15);

        boolean result = center.addDrone(drone);

        assertFalse(result);
    }

    @Test
    void shouldNotRegisterDroneWhenIdAlreadyExists() {
        RescueCenter center = new RescueCenter();
        Drone drone1 = new Drone("D-01", "Phantom X", 15);
        Drone drone2 = new Drone("D-01", "Mavic Air", 10);

        center.addDrone(drone1);
        boolean result = center.addDrone(drone2);

        assertFalse(result);
    }
}
