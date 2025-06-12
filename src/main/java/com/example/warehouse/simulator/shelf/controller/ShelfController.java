package com.example.warehouse.simulator.shelf.controller;

import com.example.warehouse.simulator.shelf.ShelfService;
import com.example.warehouse.simulator.shelf.model.request.CreateShelfCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/shelves")
@RequiredArgsConstructor
public class ShelfController implements ShelfOperations{
    private final ShelfService service;

    @Override
    @PostMapping("/")
    @ResponseStatus(CREATED)
    public void createShelf(CreateShelfCommand command) {
        service.createShelf(command);
    }

    @Override
    @DeleteMapping("/{shelfId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteShelf(@PathVariable("shelfId") long shelfId) {
        service.deleteShelf(shelfId);
    }
}
