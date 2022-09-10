package com.example.demo.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

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
    String status;
    boolean isRocket;

    private void setItemId(long itemId){};
}
