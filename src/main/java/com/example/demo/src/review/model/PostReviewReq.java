package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data @AllArgsConstructor
public class PostReviewReq {
    Long userId;
    Long itemId;
    String text;
    Float score;
    MultipartFile[] photos;
}
