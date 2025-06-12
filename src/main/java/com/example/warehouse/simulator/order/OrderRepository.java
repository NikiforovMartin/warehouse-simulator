package com.example.warehouse.simulator.order;

import com.example.warehouse.simulator.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
