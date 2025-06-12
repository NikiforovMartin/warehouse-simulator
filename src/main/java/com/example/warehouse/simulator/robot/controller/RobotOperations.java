package com.example.warehouse.simulator.robot.controller;

import com.example.warehouse.simulator.robot.model.request.CreateRobotCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Robot Operations", description = "Operations for managing warehouse robots")
public interface RobotOperations {

    @Operation(summary = "Create a new robot", description = "Add a new robot to the warehouse")
    void createRobot(@Valid @RequestBody CreateRobotCommand command);

    @Operation(summary = "Delete a robot", description = "Remove a robot from the warehouse")
    void deleteRobot(@Valid @RequestBody String serialNumber);
} 