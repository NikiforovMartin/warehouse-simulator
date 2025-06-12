package com.example.warehouse.simulator.event;

import com.example.warehouse.simulator.order.OrderRepository;
import com.example.warehouse.simulator.order.model.Order;
import com.example.warehouse.simulator.robot.RobotRepository;
import com.example.warehouse.simulator.robot.RobotService;
import com.example.warehouse.simulator.robot.model.Robot;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderOrchestrator {
    @Value("${threadWorkers:10}")
    private int threadWorkers;
    private final BlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
    private ExecutorService orderProcessorExecutor;
    private final RobotService robotService;
    private final WorkerService workerService;
    private final RobotRepository robotRepository;
    private final OrderRepository orderRepository;

    @PostConstruct
    public void init() {
        this.orderProcessorExecutor = Executors.newFixedThreadPool(threadWorkers);

        for (int i = 0; i < threadWorkers; i++) {
            orderProcessorExecutor.submit(new OrderWorker());
        }

        log.info("Started {} order processing workers", threadWorkers);
    }

    public void publishOrderCreatedEvent(Order order) {
        orderQueue.offer(order);
        log.info("Order {} queued. Queue size: {}", order.getId(), orderQueue.size());
    }

    private class OrderWorker implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Order order = orderQueue.take();

                    Robot robot = robotService.findAvailableRobot();
                    if (robot != null) {
                        log.info("Worker#{} started working", robot.getSerialNumber());
                        robotRepository.save(
                                robot.setStatus(Robot.RobotStatus.ACTIVE)
                        );
                        log.info("Robot is now active.");
                        orderRepository.save(order.setStatus(Order.OrderStatus.IN_PROGRESS));
                        workerService.doJob(robot, order);
                        orderRepository.save(order.setStatus(Order.OrderStatus.COMPLETED));
                        robotRepository.save(
                                robot
                                        .setLastUpdated(LocalDateTime.now())
                                        .setStatus(Robot.RobotStatus.INACTIVE)
                        );
                        log.info("Robot updated for finishing task.");
                        log.info("Worker#{} finished working", robot.getSerialNumber());
                    } else {
                        orderQueue.offer(order);
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        orderProcessorExecutor.shutdown();
    }
}
