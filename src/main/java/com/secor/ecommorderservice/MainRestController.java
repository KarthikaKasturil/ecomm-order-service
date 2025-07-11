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

    @Autowired
    private OrderService orderService;

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

//    @PostMapping("/create")
//    public ResponseEntity<?> createOrder(@RequestBody Order order) {
//        LOG.info("createOrder({})", order);
//        LOG.info("getProductDetails({})", order.getProductId());
//        Product product = orderService.getProductDetails(order.getProductId());
//        if (product == null) {
//            LOG.error("Product not found for productId: {}", order.getProductId());
//            return ResponseEntity.status(400).body("Product not found for productId: " + order.getProductId());
//        }
//        else{
//            LOG.info("Product found: {}", product);
//            InventoryItem inventoryItem = orderService.checkInventory(order.getProductId());
//            if (inventoryItem == null) {
//                LOG.error("No inventory found for productId: {}", order.getProductId());
//                return ResponseEntity.status(400).body("No inventory found for productId: " + order.getProductId());
//            }
//            else if (inventoryItem.getQuantity() <= 0 || inventoryItem.getQuantity() < order.getQuantity()) {
//                LOG.error("Insufficient inventory for productId: {}", order.getProductId());
//                return ResponseEntity.status(400).body("Insufficient inventory for productId: " + order.getProductId());
//            }
//            else{
//                LOG.info("Sufficient inventory found: {}", inventoryItem);
//                order.setOrderDate(LocalDateTime.now());
//                order.setUpdatedAt(LocalDateTime.now());
//                order.setStatus("PENDING_PAYMENT");
//                BigDecimal totalAmount = product.getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));
//                order.setTotalAmount(totalAmount);
//                LOG.info("Save Order");
//                Order savedOrder = orderRepository.save(order);
//                LOG.info("created order: {}", savedOrder);
//                LOG.info("create a payment for this order");
//                Payment payment = new Payment();
//                payment.setOrderId(savedOrder.getOrderId());
//                payment.setCustomerId(savedOrder.getCustomerId());
//                payment.setPaymentMethod("ONLINE");
//                payment.setAmount(totalAmount);
//                ResponseEntity<?> result = orderService.createPayment(payment);
//                LOG.info("Payment created: {}", result);
//                return ResponseEntity.ok("Order initiated successfully and created payment");
//            }
//        }
//    }

    @PostMapping("/save")
    public Order addOrder(@RequestBody Order order) {
        LOG.info("addOrder for orderID {}", order.getOrderId());
        return orderRepository.save(order);
    }

    @PutMapping("/update/status")
    public ResponseEntity<?> updateOrderStatus(OrderStatus status) {
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