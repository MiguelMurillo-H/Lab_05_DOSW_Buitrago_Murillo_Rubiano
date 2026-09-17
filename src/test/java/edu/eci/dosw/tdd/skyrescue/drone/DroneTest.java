package edu.eci.dosw.tdd.skyrescue.drone;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DroneTest {

    @Test
    void shouldCreateDroneWithGivenValues() {
        Drone drone = new Drone("D-99", "Test Drone", 30);

        assertEquals("D-99", drone.getId());
        assertEquals("Test Drone", drone.getModel());
        assertEquals(30, drone.getMaxRangeKm());
    }

    @Test
    void shouldBeAvailableByDefaultWhenCreated() {
        Drone drone = new Drone("D-99", "Test Drone", 30);

        assertTrue(drone.isAvailable());
    }

    @Test
    void shouldChangeAvailabilityWhenSet() {
        Drone drone = new Drone("D-99", "Test Drone", 30);

        drone.setAvailable(false);

        assertFalse(drone.isAvailable());
    }

    @Test
    void shouldBeEqualWhenIdsAreEqual() {
        Drone drone1 = new Drone("D-99", "Test Drone", 30);
        Drone drone2 = new Drone("D-99", "Other Model", 50);

        assertEquals(drone1, drone2);
        assertEquals(drone1.hashCode(), drone2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenIdsAreDifferent() {
        Drone drone1 = new Drone("D-99", "Test Drone", 30);
        Drone drone2 = new Drone("D-88", "Test Drone", 30);

        assertNotEquals(drone1, drone2);
    }

    @Test
    void shouldNotBeEqualToNullOrDifferentType() {
        Drone drone = new Drone("D-99", "Test Drone", 30);

        assertNotEquals(drone, null);
        assertNotEquals(drone, "un string cualquiera");
    }

    @Test
    void shouldBeEqualToItself() {
        Drone drone = new Drone("D-99", "Test Drone", 30);

        assertEquals(drone, drone);
    }
}
