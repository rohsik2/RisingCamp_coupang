package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data @AllArgsConstructor
public class SearchOrderRes {
    String searchBy;
    Long searchId;
    List<Order> orderList;
}
