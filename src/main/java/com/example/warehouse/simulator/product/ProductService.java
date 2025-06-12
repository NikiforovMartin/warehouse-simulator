package com.example.warehouse.simulator.product;

import com.example.warehouse.simulator.product.model.Product;
import com.example.warehouse.simulator.product.model.request.CreateProductCommand;
import com.example.warehouse.simulator.product.model.request.UpdateProductCommand;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository repository;

    public void createProduct(CreateProductCommand command) {
        log.info("Creating a new product. Name: {}", command.getName());

        var newProduct = new Product()
                .setName(command.getName());

        var savedProduct = repository.save(newProduct);

        log.info("Product {} created successfully.", savedProduct.getName());
    }

    public void updateProduct(UpdateProductCommand command) {
        log.info("Updating product with ID {}. New name: {}", command.getProductId(), command.getUpdatedName());

        var product = repository.findById(command.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + command.getProductId() + " not found"));

        var updatedProduct = repository.save(
                product.setName(command.getUpdatedName())
        );

        log.info("Product with ID {} updated successfully.", updatedProduct.getId());
    }

    public void deleteProduct(long productId) {
        log.info("Deleting product by ID {}.", productId);

        if (!repository.existsById(productId)) {
            throw new EntityNotFoundException("Product with ID " + productId + " not found");
        }
        repository.deleteById(productId);

        log.info("Product {} deleted successfully.", productId);
    }
}
