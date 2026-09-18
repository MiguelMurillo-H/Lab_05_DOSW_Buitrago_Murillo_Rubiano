package edu.eci.dosw.tdd.skyrescue.center;

import edu.eci.dosw.tdd.skyrescue.drone.Drone;
import edu.eci.dosw.tdd.skyrescue.mission.Mission;
import edu.eci.dosw.tdd.skyrescue.mission.MissionStatus;
import edu.eci.dosw.tdd.skyrescue.operator.RescueOperator;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public boolean addDrone(Drone drone) {
        if (drone == null) {
            return false;
        } else if (drone.getId() == null) {
            return false;
        } else if (drone.getId().isBlank()) {
            return false;
        } else if (drones.containsKey(drone.getId())) {
            return false;
        }
        else {
            drones.put(drone.getId(), drone);
            return true;
        }
    }

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
        if (operatorId != null) {
            for (RescueOperator op : operators) {
                if (op != null && op.getId() != null && op.getId().equals(operatorId)) {
                    operator = op;
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

        boolean operatorHasActiveMission = false;
        for (Mission m : missions) {
            if (m != null && m.getOperator() != null && m.getOperator().getId() != null
                    && m.getOperator().getId().equals(operatorId)
                    && m.getStatus() == MissionStatus.ACTIVE) {
                operatorHasActiveMission = true;
            }
        }

        if (operatorHasActiveMission) {
            throw new IllegalStateException("El operador ya tiene una mision activa.");
        }

        drone.setAvailable(false);

        Mission mission = new Mission(
                "M-" + System.currentTimeMillis(),
                location,
                distanceKm,
                drone,
                operator,
                LocalDateTime.now(ZoneId.systemDefault()),
                MissionStatus.ACTIVE
        );

        missions.add(mission);

        return mission;
    }

    public Mission completeMission(String missionId) {
        if (missionId == null) {
            throw new IllegalArgumentException("La mision no existe.");
        }
        if (missionId.isBlank()) {
            throw new IllegalArgumentException("La mision no existe.");
        }
        Mission foundMission = null;
        for (Mission mission : missions) {
            if (mission != null && mission.getId() != null && mission.getId().equals(missionId)) {
                foundMission = mission;
            }
        }
        if (foundMission == null) {
            throw new IllegalArgumentException("La mision no existe");
        }

        if (foundMission.getStatus() == MissionStatus.COMPLETED) {
            throw new IllegalStateException("La misión ya está completada");
        }

        foundMission.setStatus(MissionStatus.COMPLETED);
        foundMission.setEndDate(LocalDateTime.now(ZoneId.systemDefault()));

        Drone assignedDrone = foundMission.getDrone();
        if (assignedDrone != null) {
            assignedDrone.setAvailable(true);
        }
        return foundMission;
    }

    public boolean addOperator(RescueOperator operator) {
        return operators.add(operator);
    }
}