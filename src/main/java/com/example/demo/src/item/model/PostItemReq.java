package com.example.demo.src.item.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostItemReq {
    Long sellerId;
    String name;
    int price;
    int quantity;
    String text;
    String category;
    MultipartFile[] photos;
}
