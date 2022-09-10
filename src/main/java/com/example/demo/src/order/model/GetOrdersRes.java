package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data @AllArgsConstructor
public class GetOrdersRes {
    Long orderId;
    Date orderTime;

    String itemName;
    Long itemId;

    Address address;

    Integer price;

    String status;

    Long customerId;
}
