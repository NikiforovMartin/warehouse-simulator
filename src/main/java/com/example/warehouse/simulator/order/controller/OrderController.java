package com.example.warehouse.simulator.order.controller;

import com.example.warehouse.simulator.order.OrderService;
import com.example.warehouse.simulator.order.model.request.CreateOrderCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/")
@RequiredArgsConstructor
public class OrderController implements OrderOperations{
    private final OrderService service;

    @Override
    @PostMapping("/")
    public void createOrder(@Valid @RequestBody CreateOrderCommand command) {
        service.createOrder(command);
    }
}
