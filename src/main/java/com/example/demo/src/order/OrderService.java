package com.example.demo.src.order;

import com.example.demo.src.order.model.GetOrdersRes;
import com.example.demo.src.order.model.PostOrderReq;
import com.example.demo.src.order.model.PostOrderRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
