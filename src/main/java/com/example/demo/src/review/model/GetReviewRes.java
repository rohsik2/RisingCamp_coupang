package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data @AllArgsConstructor
public class GetReviewRes {
    List<Review> reviews;
}
