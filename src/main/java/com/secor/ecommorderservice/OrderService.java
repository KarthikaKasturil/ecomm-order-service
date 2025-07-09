package com.secor.ecommorderservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class OrderService
{
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);


    @Autowired
    @Qualifier("productDetailsWebClient")
    WebClient productDetailsWebClient;

    @Autowired
    @Qualifier("checkInventoryWebClient")
    WebClient checkInventoryWebClient;

    public Product getProductDetails(String productId) throws WebClientResponseException
    {
        logger.info("Call product service");

        return productDetailsWebClient.get()
                .uri("/" + productId)
                .retrieve()
                .bodyToMono(Product.class)
                .block(); // Assuming the product details are valid for demonstration purposes
    }

    public InventoryItem checkInventory(String productId) throws WebClientResponseException
    {
        logger.info("Call inventory service");

        return checkInventoryWebClient.get()
                .uri("/" + productId)
                .retrieve()
                .bodyToMono(InventoryItem.class)
                .block(); // Assuming the inventory check is valid for demonstration purposes
    }

}
