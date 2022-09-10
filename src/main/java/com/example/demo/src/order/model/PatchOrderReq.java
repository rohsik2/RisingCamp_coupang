package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class PatchOrderReq {
    Long orderId;
    String status;
}
