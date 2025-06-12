package com.example.warehouse.simulator.warehouse;

import com.example.warehouse.simulator.shelf.ShelfRepository;
import com.example.warehouse.simulator.shelf.model.Shelf;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WarehouseUtils {
    @Value("${warehouse.width:20}")
    private int width;

    @Value("${warehouse.height:20}")
    private int height;

    private final ShelfRepository shelfRepository;

    private boolean[][] occupiedGrid;

    @PostConstruct
    public void initializeGrid() {
        occupiedGrid = new boolean[width][height];

        List<Shelf> shelves = shelfRepository.findAll();
        for (Shelf shelf : shelves) {
            occupiedGrid[shelf.getLocationX()][shelf.getLocationY()] = true;
        }
    }

    public boolean canMoveRobotTo(int x, int y) {
        return x >= 0 && x < width &&
                y >= 0 && y < height &&
                !occupiedGrid[x][y];
    }

    public boolean canPlaceShelfAt(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height &&
                !occupiedGrid[x][y];
    }

    public void markPosition(int x, int y, boolean occupied) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            occupiedGrid[x][y] = occupied;
        }
    }
}
