package com.example.warehouse.simulator.shelf;

import com.example.warehouse.simulator.product.ProductRepository;
import com.example.warehouse.simulator.product.model.Product;
import com.example.warehouse.simulator.shelf.model.Shelf;
import com.example.warehouse.simulator.shelf.model.request.CreateShelfCommand;
import com.example.warehouse.simulator.warehouse.WarehouseUtils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ShelfService {
    private final ShelfRepository repository;
    private final ProductRepository productRepository;
    private final WarehouseUtils warehouseUtils;

    public void createShelf(CreateShelfCommand command) {
        String newShelfCoordinates = String.format("X:%d / Y:%d", command.getLocationX(), command.getLocationY());
        log.info("Creating a new shelf. Coordinates: {}", newShelfCoordinates);

        validateShelfCoordinates(command);
        
        var shelf = new Shelf()
                .setLocationX(command.getLocationX())
                .setLocationY(command.getLocationY())
                .setProduct(findProduct(command.getProductId()))
                .setQuantity(command.getQuantity());

        var savedShelf = repository.save(shelf);

        warehouseUtils.markPosition(savedShelf.getLocationX(), savedShelf.getLocationY(), true);

        log.info("Shelf created successfully on {}. ID: {}", newShelfCoordinates, savedShelf.getId());
    }

    private Product findProduct(@Min(value = 1, message = "Provide product ID.") long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productId + " not found."));
    }

    private void validateShelfCoordinates(CreateShelfCommand command) {
        boolean canPlace = warehouseUtils.canPlaceShelfAt(command.getLocationX(), command.getLocationY());

        if (!canPlace) {
            throw new EntityExistsException("Shelf already exists on said coordinates.");
        }
    }

    public void deleteShelf(long shelfId) {
        log.info("Deleting shelf by ID {}.", shelfId);

        var shelf = repository.findById(shelfId)
                        .orElseThrow(() -> new EntityNotFoundException("Shelf with ID " + shelfId + " not found."));

        repository.delete(shelf);

        warehouseUtils.markPosition(shelf.getLocationX(), shelf.getLocationY(), false);

        log.info("Shelf deleted successfully.");
    }
}
