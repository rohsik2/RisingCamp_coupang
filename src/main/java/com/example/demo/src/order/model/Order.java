package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Order {
    Long orderId;
    Long customer;
    Long seller;
    Long address;
    Long item;
    Integer price;
}
