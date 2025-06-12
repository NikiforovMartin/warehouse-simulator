package com.example.warehouse.simulator.robot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "robots")
public class Robot {
    @Id
    @Column(name = "serial_number")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RobotStatus status = RobotStatus.INACTIVE;

    @Column(name = "base_x")
    private int baseX = 0;

    @Column(name = "base_y")
    private int baseY = 0;

    @Column(name = "location_x")
    private int locationX = 0;

    @Column(name = "location_y")
    private int locationY = 0;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated = LocalDateTime.now();

    public enum RobotStatus {
        ACTIVE,
        INACTIVE
    }
}