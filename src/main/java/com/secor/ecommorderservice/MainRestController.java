package com.secor.ecommorderservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class MainRestController {

    private final Logger LOG = LoggerFactory.getLogger(MainRestController.class);

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        LOG.info("getAllOrders");
        return orderRepository.findAll();
    }

    @GetMapping("/{orderId}")
    public Order getOrderForOrderId(@PathVariable String orderId) {
        LOG.info("getOrderForOrderId({})", orderId);
        return orderRepository.findByOrderId(orderId);
    }

    @GetMapping("/customer/{customerId}")
    public List<Order> getOrderForCustomerId(@PathVariable String customerId) {
        LOG.info("getOrderForCustomerId({})", customerId);
        return orderRepository.findAllByCustomerId(customerId);
    }

    @PostMapping("/save")
    public Order addOrder(@RequestBody Order order) {
        LOG.info("addOrder for orderID {}", order.getOrderId());
        return orderRepository.save(order);
    }

    @PutMapping("/update/status")
    public ResponseEntity<?> updateOrderStatus(@RequestBody OrderStatus status) {
        LOG.info("updateOrderStatus({})", status);
        Order order = orderRepository.findByOrderId(status.getOrderId());
        if (order != null) {
          order.setStatus(status.getStatus());
          order.setUpdatedAt(LocalDateTime.now());
          Order savedOrder = orderRepository.save(order);
          return ResponseEntity.ok("Order status updated to " + savedOrder.getStatus() + " for order " + savedOrder.getOrderId());
        }
        else{
            return ResponseEntity.status(400).body("Order not found with Id " + status.getOrderId());
        }
    }
}