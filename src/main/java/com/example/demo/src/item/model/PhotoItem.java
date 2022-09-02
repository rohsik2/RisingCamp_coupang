package com.example.demo.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data @AllArgsConstructor
public class PhotoItem {
    Long photoItemId;
    String url;
    Long itemId;
    Long uploaderId;
    Date createdAt;
    char status;
    Date updatedAt;
}
