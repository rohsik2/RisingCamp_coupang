package com.example.demo.src.item;

import com.example.demo.src.item.model.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


//Except Read
@Service @RequiredArgsConstructor
public class ItemService {
    private final ItemDao itemDao;

    public SearchItemRes searchItem(SearchItemReq req){
        if(req.getSearchKeyWord() == null)
            req.setSearchKeyWord("%");
        if(req.getCategory() == null)
            req.setCategory("%");
        if(req.getOrder() == null)
            req.setOrder("score");

        return itemDao.searchItems(req);
    }


    public PostItemRes makeItem(PostItemReq postItemReq) {
        long itemId = itemDao.createItem(postItemReq);
        return new PostItemRes(itemId, "/api/items/" + String.valueOf(itemId));
    }

    public GetItemRes findById(Long postId) {
        return itemDao.getItem(postId);
    }

    public boolean deleteItem(Long itemId) {
        return itemDao.deleteItem(itemId);
    }
}
