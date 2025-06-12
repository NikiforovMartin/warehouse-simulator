package com.example.warehouse.simulator.product.controller;

import com.example.warehouse.simulator.product.model.request.CreateProductCommand;
import com.example.warehouse.simulator.product.model.request.UpdateProductCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product Operations", description = "Operations for managing warehouse products")
public interface ProductOperations {

    @Operation(summary = "Create a new product", description = "Add a new product to the warehouse catalog")
    void createProduct(CreateProductCommand command);

    @Operation(summary = "Update an existing product", description = "Modify product details by ID")
    void updateProduct(UpdateProductCommand command);

    @Operation(summary = "Delete a product", description = "Remove a product from the warehouse catalog")
    void deleteProduct(long productId);
}
