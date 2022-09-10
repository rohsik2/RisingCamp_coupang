package com.example.demo.src.order;

import com.example.demo.config.BaseResponse;
import com.example.demo.src.order.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class OrderService {
    private final OrderDao orderDao;
    public PostOrderRes createOrder(PostOrderReq req) {
        Long orderId = orderDao.createOrder(req);
        return new PostOrderRes(orderId, "api/orders/"+String.valueOf(orderId));
    }

    public boolean deleteOrder(Long orderId) {
        return orderDao.deleteOrder(orderId);
    }

    public GetOrdersRes findOrderById(Long orderId) {
        return orderDao.findOrderById(orderId);
    }

    public SearchOrderRes searchOrdersByItemId(Long itemId) {
        List<Order> orders = orderDao.searchOrders("item", itemId);
        return new SearchOrderRes("itemId", itemId, orders);
    }

    public SearchOrderRes searchOrdersByCustomerId(Long customerId) {
        List<Order> orders = orderDao.searchOrders("customer", customerId);
        return new SearchOrderRes("customer", customerId, orders);
    }

    public SearchOrderRes searchOrdersBySellerId(Long sellerId) {
        List<Order> orders = orderDao.searchOrders("item", sellerId);
        return new SearchOrderRes("seller", sellerId, orders);
    }


    public boolean isOrderExist(Long orderId) {
        return orderDao.isOrderExist(orderId);
    }

    public Boolean patchOrderStatus(PatchOrderReq req) {
        if("ABCDEFG".indexOf(req.getStatus()) != -1){
            return orderDao.changeOrderStatus(req) == 1;
        }
        else
            return false;
    }
}
