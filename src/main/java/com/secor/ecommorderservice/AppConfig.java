package com.secor.ecommorderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Configuration
public class AppConfig
{
    @Autowired
    EurekaDiscoveryClient discoveryClient;

    @Bean
    @Scope("prototype")
    public WebClient productDetailsWebClient(WebClient.Builder webClientBuilder)
    {
        return webClientBuilder
                .baseUrl("http://localhost:8092/products")
                .filter(new LoggingWebClientFilter())
                .build();
    }

    @Bean
    @Scope("prototype")
    public WebClient checkInventoryWebClient(WebClient.Builder webClientBuilder)
    {
        return webClientBuilder
                .baseUrl(String.format("http://%s:%s/inventory/product", "localhost", 8094))
                .filter(new LoggingWebClientFilter())
                .build();
    }


}
