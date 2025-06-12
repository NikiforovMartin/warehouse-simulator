package com.example.warehouse.simulator.product.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProductCommand {
    @Min(value = 1, message = "Provide product ID.")
    private long productId;
    @NotBlank(message = "Provide product name.")
    private String updatedName;
}
