package com.example.warehouse.simulator.product.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProductCommand {
    @NotBlank(message = "Provide product name.")
    private String name;
}
