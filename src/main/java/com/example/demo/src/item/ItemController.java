package com.example.demo.src.item;

import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.item.model.*;
import com.example.demo.utils.JwtService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @Autowired
    private final JwtService jwtService;

    @RequestMapping(method = RequestMethod.POST)
    BaseResponse<?> postItem(PostItemReq postItemReq){
        // TODO : validation code 작성하기
        PostItemRes res = itemService.makeItem(postItemReq);
        return new BaseResponse<>(res);
    }

    @RequestMapping(value = "detail/{itemId}", method = RequestMethod.GET)
    BaseResponse<?> getItem(@PathVariable Long itemId){
        GetItemRes res = itemService.findById(itemId);
        return new BaseResponse<>(res);
    }

    @RequestMapping(value = "detail/{itemId}", method = RequestMethod.DELETE)
    BaseResponse<?> deleteItem(@PathVariable Long itemId){
        itemService.deleteItem(itemId);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    BaseResponse<?> searchItem(SearchItemReq searchItemReq){
        SearchItemRes searchItemRes = itemService.searchItem(searchItemReq);
        return new BaseResponse<>(searchItemRes);
    }
}

