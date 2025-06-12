package com.example.warehouse.simulator.shelf.controller;

import com.example.warehouse.simulator.shelf.model.request.CreateShelfCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Shelf Operations", description = "Operations for managing warehouse shelves")
public interface ShelfOperations {

    @Operation(summary = "Create a new shelf", description = "Add a new shelf to the warehouse at specified coordinates")
    void createShelf(CreateShelfCommand command);

    @Operation(summary = "Delete a shelf", description = "Remove a shelf from the warehouse")
    void deleteShelf(long shelfId);
}
