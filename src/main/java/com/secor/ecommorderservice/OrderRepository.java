package com.secor.ecommorderservice;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String>
{
    Order findByOrderId(String orderId);
    List<Order> findAllByCustomerId(String customerId);
}
