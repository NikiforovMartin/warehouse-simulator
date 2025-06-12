package com.example.warehouse.simulator.order;

import com.example.warehouse.simulator.event.ApplicationOrderEventPublisher;
import com.example.warehouse.simulator.order.model.Order;
import com.example.warehouse.simulator.order.model.request.CreateOrderCommand;
import com.example.warehouse.simulator.product.ProductRepository;
import com.example.warehouse.simulator.product.model.Product;
import com.example.warehouse.simulator.robot.RobotService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final RobotService robotService;
    private final ApplicationOrderEventPublisher eventPublisher;

    public void createOrder(@Valid CreateOrderCommand command) {
        log.info("Creating order with {} items", command.getItems().size());

        Set<Long> orderProductsIds = command.getItems().keySet();
        List<Product> existingProducts = productRepository.findAllById(orderProductsIds);

        if (existingProducts.isEmpty()) {
            throw new EntityNotFoundException("No products found by IDs " + orderProductsIds);
        }

        List<Long> existingProductsIds = existingProducts.stream().map(Product::getId).toList();
        var nonExistingProducts = orderProductsIds.stream().filter(id -> !existingProductsIds.contains(id)).toList();

        if(!nonExistingProducts.isEmpty()) {
            log.warn("{} products not found: {}", nonExistingProducts.size(), nonExistingProducts);
        }

        var orderItems = existingProducts.stream()
                .collect(Collectors.toMap(
                        product -> product,
                        product -> command.getItems().get(product.getId())
                ));

        var assignedRobot = robotService.findAvailableRobot();

        var order = new Order()
                .setItems(orderItems);

        var savedOrder = orderRepository.save(order);

        log.info("Order with {} items successfully saved. Assigned robot {}.", savedOrder.getItems().size(), assignedRobot.getSerialNumber());

        eventPublisher.publishOrderCreatedEvent(order);
    }
}
