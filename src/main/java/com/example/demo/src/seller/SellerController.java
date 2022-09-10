package com.example.demo.src.seller;

import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.seller.model.dto.GetSellerRes;
import com.example.demo.src.seller.model.dto.PostSellerReq;
import com.example.demo.src.seller.model.dto.PostSellerRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api/seller") @RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    BaseResponse<?> createNewSeller(PostSellerReq req){
        PostSellerRes res = sellerService.createSeller(req);
        return new BaseResponse<>(res);
    }

    @RequestMapping(value = "{sellerId}", method = RequestMethod.GET)
    BaseResponse<?> getSeller(@PathVariable Long sellerId){
        GetSellerRes res = sellerService.findById(sellerId);
        return new BaseResponse<>(res);
    }

    @RequestMapping(value = "{sellerId}", method = RequestMethod.DELETE)
    BaseResponse<?> deleteSeller(@PathVariable Long sellerId){
        sellerService.deleteSeller(sellerId);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }
}
