package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Address {
    Long addressId;
    String receiverName;
    String address;
    String receiverPhoneNumber;
}
