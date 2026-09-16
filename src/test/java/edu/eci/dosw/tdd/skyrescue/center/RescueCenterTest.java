package edu.eci.dosw.tdd.skyrescue.center;

import edu.eci.dosw.tdd.skyrescue.drone.Drone;
import edu.eci.dosw.tdd.skyrescue.mission.Mission;
import edu.eci.dosw.tdd.skyrescue.operator.RescueOperator;
import edu.eci.dosw.tdd.skyrescue.mission.MissionStatus;

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
    @Test
    void shouldAssignMissionSuccessfullyWhenDataIsValid() {
        RescueCenter center = new RescueCenter();
        Drone drone = new Drone("D-10", "Phantom X", 50);
        RescueOperator operator = new RescueOperator("OP-10", "Ana");
        
        center.addDrone(drone);
        center.addOperator(operator);

        Mission mission = center.assignMission("OP-10", "D-10", "Zona Sur", 20);

        assertNotNull(mission);
        assertEquals(MissionStatus.ACTIVE, mission.getStatus());
        assertFalse(drone.isAvailable());
    }

    @Test
    void shouldCompleteMissionSuccessfully() {
        RescueCenter center = new RescueCenter();
        Drone drone = new Drone("D-20", "Mavic", 100);
        RescueOperator operator = new RescueOperator("OP-20", "Luis");
        
        center.addDrone(drone);
        center.addOperator(operator);

        Mission mission = center.assignMission("OP-20", "D-20", "Zona Este", 10);
        Mission completedMission = center.completeMission(mission.getId());

        assertNotNull(completedMission);
        assertEquals(MissionStatus.COMPLETED, completedMission.getStatus());
        assertNotNull(completedMission.getEndDate());
        assertTrue(drone.isAvailable());
    }

    //RED
    @Test
    void shouldThrowExceptionWhenCompletingAlreadyCompletedMission(){
        RescueCenter center = new RescueCenter();
        Drone drone = new Drone("D-50", "Romeo", 100);
        RescueOperator operator = new RescueOperator("OP-50", "Pedro");

        center.addDrone(drone);
        center.addOperator(operator);

        Mission mission = center.assignMission("OP-50", "D-50", "Zona Central", 10);
        center.completeMission(mission.getId());

        try{
            center.completeMission(mission.getId());
            fail("Deberia haber lanzado IllegalStateException porque la mision ya está completada");
        } catch(IllegalStateException e){
            assertNotNull(e.getMessage());
        }
    }

    @Test
    void shouldNotAffectOtherActiveMissionWhenCompletinAMission(){
        RescueCenter center = new RescueCenter();
        Drone drone1 = new Drone("D-40", "Alpha", 50);
        Drone drone2 = new Drone("D-41", "Beta", 50);
        RescueOperator operator1 = new RescueOperator("OP-40", "Alex");
        RescueOperator operator2 = new RescueOperator("OP-41", "Fabio");

        center.addDrone(drone1);
        center.addDrone(drone2);
        center.addOperator(operator1);
        center.addOperator(operator2);

        Mission mission1 = center.assignMission("OP-40", "D-40", "Zona 1", 10);
        Mission mission2 = center.assignMission("OP-41", "D-41", "Zona 2", 10);
        center.completeMission(mission1.getId());

        assertEquals(MissionStatus.ACTIVE, mission2.getStatus());
        assertFalse(drone2.isAvailable());
    }

    @Test
    void shouldThrowExceptionWhenDistanceExceedsDroneRange() {
        RescueCenter center = new RescueCenter();
        Drone drone = new Drone("D-50", "Phantom", 10);
        RescueOperator operator = new RescueOperator("OP-50", "Julian");

        center.addDrone(drone);
        center.addOperator(operator);

        try {
            center.assignMission("OP-50", "D-50", "Zona Lejana", 20);
            fail("Deberia haber lanzado IllegalArgumentException porque la distancia excede la autonomia del dron");
        }catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }
}
