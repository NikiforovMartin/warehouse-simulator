package com.example.warehouse.simulator.robot;

import com.example.warehouse.simulator.robot.model.Robot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RobotRepository extends JpaRepository<Robot, String> {
    Optional<Robot> findFirstByStatusOrderByLastUpdatedAsc(Robot.RobotStatus status);
} 