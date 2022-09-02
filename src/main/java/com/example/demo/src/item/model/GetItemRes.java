package com.example.demo.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class GetItemRes {
    Long itemId;
    String name;
    int price;
    int quantity;
    String text;
    String category;
    String sellerName;
    Long sellerId;
}
