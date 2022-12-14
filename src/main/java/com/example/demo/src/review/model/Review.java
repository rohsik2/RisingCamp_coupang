package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Review {
    Long reviewId;
    String text;
    Float score;
    Long itemId;
    Long customerId;
}
