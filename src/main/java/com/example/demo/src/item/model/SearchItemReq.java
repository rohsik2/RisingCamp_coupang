package com.example.demo.src.item.model;

import lombok.Data;

@Data
public class SearchItemReq {
    String searchKeyWord;
    String category;
    String order;
}
