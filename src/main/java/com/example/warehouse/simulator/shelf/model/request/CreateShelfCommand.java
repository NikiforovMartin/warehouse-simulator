package com.example.warehouse.simulator.shelf.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateShelfCommand {
    @NotNull(message = "Provide location (X axis).")
    private Integer locationX;
    @NotNull(message = "Provide location (Y axis).")
    private Integer locationY;
    @Min(value = 1, message = "Provide product ID.")
    private long productId;
    @Min(value = 1, message = "Provide item quantity.")
    private long quantity;
}
