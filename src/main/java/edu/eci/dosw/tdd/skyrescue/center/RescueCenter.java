package edu.eci.dosw.tdd.skyrescue.center;

import edu.eci.dosw.tdd.skyrescue.drone.Drone;
import edu.eci.dosw.tdd.skyrescue.mission.Mission;
import edu.eci.dosw.tdd.skyrescue.mission.MissionStatus;
import edu.eci.dosw.tdd.skyrescue.operator.RescueOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Coordinates drones, operators and emergency missions.
 */
public class RescueCenter {

    private final List<RescueOperator> operators;
    private final Map<String, Drone> drones;
    private final List<Mission> missions;

    public RescueCenter() {
        this.operators = new ArrayList<>();
        this.drones = new HashMap<>();
        this.missions = new ArrayList<>();
    }

    /**
     * Registers a drone in the rescue center.
     *
     * Rules:
     * - The drone cannot be null.
     * - The drone id cannot be null or blank.
     * - Two drones cannot have the same id.
     * - A valid drone is stored as available.
     *
     * @param drone drone to register.
     * @return true if it was registered; false otherwise.
     */
    public boolean addDrone(Drone drone) {
        if (drone == null) {
            return false;
        }
        else if (drone.getId() == null) {
            return false;
        }
        else if (drone.getId().isBlank()) {
            return false;
        }
        else if (drones.containsKey(drone.getId())) {
            return false;
        }
        else{
        drones.put(drone.getId(), drone);
            return true;
        }
    }

    /**
     * Assigns an emergency mission to an operator and an available drone.
     *
     * Rules:
     * - operatorId, droneId and location must be valid.
     * - The operator must exist.
     * - The drone must exist and be available.
     * - distanceKm must be greater than zero.
     * - distanceKm cannot exceed the drone maxRangeKm.
     * - The same operator cannot have two ACTIVE missions.
     * - On success, create an ACTIVE mission with the current date.
     * - On success, the selected drone becomes unavailable.
     * - The created mission must be stored in the center.
     *
     * Suggested error policy:
     * - Invalid/nonexistent data -> IllegalArgumentException.
     * - Valid resource but invalid state -> IllegalStateException.
     *
     * @param operatorId operator identifier.
     * @param droneId drone identifier.
     * @param location emergency location description.
     * @param distanceKm mission distance in kilometers.
     * @return created mission.
     */
    public Mission assignMission(
        String operatorId,
        String droneId,
        String location,
        int distanceKm) {

        if (droneId == null) {
            throw new IllegalArgumentException("El dron no existe.");
        }
        if (!drones.containsKey(droneId)) {
            throw new IllegalArgumentException("El dron no existe.");
        }

        RescueOperator operator = null;
        if (operators != null) {
            if (operatorId != null) {
                for (RescueOperator op : operators) {
                    if (op != null) {
                        if (op.getId() != null) {
                            if (op.getId().equals(operatorId)) {
                                operator = op;
                            }
                        }
                    }
                }
            }
        }

        if (operator == null) {
            throw new IllegalArgumentException("El operador no existe.");
        }

        Drone drone = drones.get(droneId);

        if (!drone.isAvailable()) {
            throw new IllegalStateException("El dron ya esta ocupado.");
        }

        if (distanceKm <= 0) {
            throw new IllegalArgumentException("La distancia no es valida para este dron.");
        }
        if (distanceKm > drone.getMaxRangeKm()) {
            throw new IllegalArgumentException("La distancia no es valida para este dron.");
        }

        // mira si ya tiene mision activa
        boolean operatorHasActiveMission = false;
        if (missions != null) {
            for (Mission m : missions) {
                if (m != null) {
                    if (m.getOperator() != null) {
                        if (m.getOperator().getId() != null) {
                            if (m.getOperator().getId().equals(operatorId)) {
                                if (m.getStatus() == MissionStatus.ACTIVE) {
                                    operatorHasActiveMission = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (operatorHasActiveMission) {
            throw new IllegalStateException("El operador ya tiene una mision activa.");
        }

        // Asignación estado
        drone.setAvailable(false);

        Mission mission = new Mission(
            "M-" + System.currentTimeMillis(),
            location,
            distanceKm,
            drone,
            operator,
            java.time.LocalDateTime.now(),
            MissionStatus.ACTIVE
        );

        missions.add(mission);

        return mission;
    }

    /**
     * Completes an active mission.
     *
     * Rules:
     * - missionId must be valid.
     * - The mission must exist.
     * - An already COMPLETED mission cannot be completed again.
     * - The mission status changes to COMPLETED.
     * - The end date is the current date/time.
     * - The drone assigned to the mission becomes available again.
     *
     * Suggested error policy:
     * - Invalid/nonexistent mission -> IllegalArgumentException.
     * - Mission already completed -> IllegalStateException.
     *
     * @param missionId mission identifier.
     * @return completed mission.
     */
    public Mission completeMission(String missionId) {
        if (missionId == null) {
            throw new IllegalArgumentException("La mision no existe.");
        }
        if (missionId.isBlank()) {
            throw new IllegalArgumentException("La mision no existe.");
        }
        Mission foundMission = null;
        if (missions != null) {
            for (Mission mission : missions) {
                if (mission != null) {
                    if (mission.getId() != null) {
                        if (mission.getId().equals(missionId)) {
                            foundMission = mission;
                        }
                    }
                }
            }
        }
        if (foundMission == null) {
            throw new IllegalArgumentException("La mision no existe");
        }

        foundMission.setStatus(MissionStatus.COMPLETED);
        foundMission.setEndDate(java.time.LocalDateTime.now());

        // libera el dron
        if (drones != null) {
            for (Drone drone : drones.values()) {
                if (drone != null) {
                    drone.setAvailable(true);
                }
            }
        }
        return foundMission;
    }

    public boolean addOperator(RescueOperator operator) {
        return operators.add(operator);
    }
}