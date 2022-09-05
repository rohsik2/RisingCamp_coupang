package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class PostOrderReq {
    Long customerId;
    Long sellerId;
    String address;
    String receiverName;
    String receiverPhoneNumber;
    Long itemId;
    int price;

}


