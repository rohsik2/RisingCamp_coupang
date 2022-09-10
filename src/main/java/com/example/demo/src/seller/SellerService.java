package com.example.demo.src.seller;

import com.example.demo.src.item.ItemDao;
import com.example.demo.src.item.model.GetItemRes;
import com.example.demo.src.item.model.Item;
import com.example.demo.src.seller.model.Seller;
import com.example.demo.src.seller.model.dto.GetSellerRes;
import com.example.demo.src.seller.model.dto.PostSellerReq;
import com.example.demo.src.seller.model.dto.PostSellerRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class SellerService {
    private final SellerDao sellerDao;
    private final ItemDao itemDao;

    public PostSellerRes createSeller(PostSellerReq req) {
        Long sellerId = sellerDao.save(req);
        return new PostSellerRes(sellerId, "/api/seller/"+sellerId);
    }

    public GetSellerRes findById(Long sellerId){
        Seller seller = sellerDao.findById(sellerId);
        return new GetSellerRes(seller.getSellerId(), seller.getName(), seller.getIntroduce());
    }

    public Boolean deleteSeller(Long sellerId) {
        try {
            List<Item> itemsList = itemDao.findItemsBySellerId(sellerId);

            for (Item item : itemsList) {
                if (!itemDao.deleteSellerId(item.getItemId())) {
                    log.error("Seller Id Deletion Failed {}", item.getSellerId());
                }
            }
            sellerDao.delete(sellerId);
        }
        catch(Exception e){
            return false;
        }
        return true;
    }

    public boolean isSellerIdExist(Long sellerId) {
        return sellerDao.isSellerIdExist(sellerId);
    }
}
