package com.example.warehouse.simulator.product;

import com.example.warehouse.simulator.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
