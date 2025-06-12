package com.example.warehouse.simulator.robot.controller;

import com.example.warehouse.simulator.robot.RobotService;
import com.example.warehouse.simulator.robot.model.request.CreateRobotCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/robots")
@RequiredArgsConstructor
public class RobotController implements RobotOperations {
    private final RobotService service;

    @Override
    @PostMapping("/")
    @ResponseStatus(CREATED)
    public void createRobot(@Valid @RequestBody CreateRobotCommand command) {
        service.createRobot(command);
    }

    @Override
    @DeleteMapping("/{serialNumber}")
    @ResponseStatus(NO_CONTENT)
    public void deleteRobot(@PathVariable("serialNumber") String serialNumber) {
        service.deleteRobot(serialNumber);
    }
} 