package com.example.demo.src.seller.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data @AllArgsConstructor
public class Seller {
    Long sellerId;
    String name;
    Date updatedAt;
    Date createdAt;
    String status;
    String introduce;
}
