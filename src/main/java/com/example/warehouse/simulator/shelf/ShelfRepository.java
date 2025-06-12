package com.example.warehouse.simulator.shelf;

import com.example.warehouse.simulator.shelf.model.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    boolean existsByLocationXAndLocationY(int locationX, int locationY);
}
