package com.example.demo.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class Item {
    long itemId;
    String name;
    int price;
    int quantity;
    long sellerId;
    String text;
    String category;
    Date updatedAt;
    Date createdAt;
    char status;
    boolean isRocket;
}
