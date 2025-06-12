package com.example.warehouse.simulator.robot.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRobotCommand {
    @NotBlank(message = "Provide robot serial number.")
    private String serialNumber;
    private Integer baseX;
    private Integer baseY;
} 