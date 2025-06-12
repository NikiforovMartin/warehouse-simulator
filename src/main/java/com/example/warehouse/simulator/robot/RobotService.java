package com.example.warehouse.simulator.robot;

import com.example.warehouse.simulator.robot.model.Robot;
import com.example.warehouse.simulator.robot.model.request.CreateRobotCommand;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RobotService {
    private final RobotRepository repository;

    public void createRobot(CreateRobotCommand command) {
        log.info("Creating a new robot with serial number: {}", command.getSerialNumber());

        if (repository.existsById(command.getSerialNumber())) {
            throw new EntityExistsException("Robot with serial number " + command.getSerialNumber() + " already exists");
        }

        var newRobot = new Robot()
                .setSerialNumber(command.getSerialNumber())
                .setBaseX(command.getBaseX() != null ? command.getBaseX() : 0)
                .setBaseY(command.getBaseY() != null ? command.getBaseY() : 0)
                .setLocationX(command.getBaseX() != null ? command.getBaseX() : 0)
                .setLocationY(command.getBaseY() != null ? command.getBaseY() : 0);

        var savedRobot = repository.save(newRobot);

        log.info("Robot {} created successfully.", savedRobot.getSerialNumber());
    }

    public void deleteRobot(String serialNumber) {
        log.info("Deleting robot with serial number: {}", serialNumber);

        if (!repository.existsById(serialNumber)) {
            throw new EntityNotFoundException("Robot with serial number " + serialNumber + " not found");
        }

        repository.deleteById(serialNumber);

        log.info("Robot {} deleted successfully.", serialNumber);
    }

    public Robot findAvailableRobot() {
        return repository.findFirstByStatusOrderByLastUpdatedAsc(Robot.RobotStatus.INACTIVE)
                .orElseThrow(() -> new EntityNotFoundException("No available robots found."));
    }
} 