package com.example.demo.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data @AllArgsConstructor
public class SearchItemRes {
    @Data @AllArgsConstructor
    public static class ItemThumbnail{
        long itemId;
        String basePhotoUrl;
        String name;
        int price;
        Double score;
        int reviewNum;
    }
    String searchKeyWord;
    String category;
    List<ItemThumbnail> items;
}

