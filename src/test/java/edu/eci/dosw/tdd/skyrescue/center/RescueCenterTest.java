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

    @Test
    void shouldThrowExceptionWhenCompletingNonExistentMission() {
        RescueCenter center = new RescueCenter();

        try {
            center.completeMission("M-INEXISTENTE");
            fail("Deberia haber lanzado IllegalArgumentException porque la mision no existe.");
        } 
        catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test 
    void shouldNotRegisterDroneWhenDroneIsNull(){
        RescueCenter center = new RescueCenter();
        boolean result = center.addDrone(null);
        assertFalse(result);
    }

    //RED 
    @Test 
    void shouldThrowExceptionWhenDroneIsAlreadyBusy(){
        RescueCenter center = new RescueCenter();
        RescueOperator operator1 = new RescueOperator("OP-01", "Luisa");
        RescueOperator operator2 = new RescueOperator("OP-02", "Jose");
        center.addOperator(operator1);
        center.addOperator(operator2);

        Drone drone = new Drone("D-01", "Phantom X", 15);
        center.addDrone(drone);

        //Primera asignación deja el dron ocupado
        center.assignMission("OP-01", "D-01", "Norte", 15);

        try{
            center.assignMission("OP-02", "D-01", "Sur", 5);
            fail("Debería haber lanzado IllegalStateException porque el dron ya esta ocupado");
        }catch(IllegalStateException e){
            assertNotNull(e.getMessage());
        }
    }
}
