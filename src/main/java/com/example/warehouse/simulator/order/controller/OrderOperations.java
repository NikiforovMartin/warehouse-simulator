package com.example.warehouse.simulator.order.controller;

import com.example.warehouse.simulator.order.model.request.CreateOrderCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Order Operations", description = "API for managing warehouse orders")
public interface OrderOperations {

    @Operation(
            summary = "Create a new order",
            description = "Creates a new order with the specified items and assigns it to an available robot"
    )
    void createOrder(
            @Parameter(description = "Order creation request", required = true)
            CreateOrderCommand command
    );
}
