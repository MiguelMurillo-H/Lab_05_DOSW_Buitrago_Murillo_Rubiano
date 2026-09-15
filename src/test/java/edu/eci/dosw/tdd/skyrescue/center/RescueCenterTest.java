package edu.eci.dosw.tdd.skyrescue.center;

import edu.eci.dosw.tdd.skyrescue.drone.Drone;
import edu.eci.dosw.tdd.skyrescue.operator.RescueOperator;

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
    void shouldThrowExceptionWhenAssigningMissionToNonExistentDrone() {
        RescueCenter center = new RescueCenter();
        RescueOperator operator = new RescueOperator("OP-01", "Carlos");
        center.addOperator(operator);

        try {
            center.assignMission("OP-01", "DRONE-INEXISTENTE", "Zona Norte", 5);
            fail("Deberia haber lanzado IllegalArgumentException porque el dron no existe.");
            }   
        catch (IllegalArgumentException e) {
        // La prueba pasa si se lanza la excepcion
            assertNotNull(e.getMessage());
            }
    }
}
