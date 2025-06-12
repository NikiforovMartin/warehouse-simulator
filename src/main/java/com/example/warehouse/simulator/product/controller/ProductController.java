package com.example.warehouse.simulator.product.controller;

import com.example.warehouse.simulator.product.ProductService;
import com.example.warehouse.simulator.product.model.request.CreateProductCommand;
import com.example.warehouse.simulator.product.model.request.UpdateProductCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController implements ProductOperations {
    private final ProductService service;

    @Override
    @PostMapping("/")
    @ResponseStatus(CREATED)
    public void createProduct(@Valid @RequestBody CreateProductCommand command) {
        service.createProduct(command);
    }

    @Override
    @PutMapping("/")
    @ResponseStatus(ACCEPTED)
    public void updateProduct(@Valid @RequestBody UpdateProductCommand command) {
        service.updateProduct(command);
    }

    @Override
    @DeleteMapping("/{productId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteProduct(@PathVariable("productId") long productId) {
        service.deleteProduct(productId);
    }
}
