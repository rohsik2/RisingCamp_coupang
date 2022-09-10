package com.example.demo.src.seller.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class GetSellerRes {
    Long sellerId;
    String name;
    String introduce;
}
