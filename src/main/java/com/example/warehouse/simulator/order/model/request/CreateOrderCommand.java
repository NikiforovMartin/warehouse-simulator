package com.example.warehouse.simulator.order.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Map;

@Data
public class CreateOrderCommand {
    @NotEmpty(message = "Order items cannot be empty")
    private Map<@NotNull @Positive Long, @NotNull @Positive Long> items;
}
