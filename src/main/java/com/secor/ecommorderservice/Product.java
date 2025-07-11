package com.secor.ecommorderservice;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Product
{
    private String productName;
    private String description;
    private BigDecimal price;
    private String category;
}
