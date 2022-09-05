package com.example.demo.src.order;

import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.order.model.GetOrdersRes;
import com.example.demo.src.order.model.PostOrderReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller @RequestMapping(value="/api/orders") @RequiredArgsConstructor
public class OrderController {
    public final OrderService orderService;
    @RequestMapping(value = "", method= RequestMethod.POST)
    BaseResponse<?> newOrder(PostOrderReq req){
        return new BaseResponse (orderService.createOrder(req));
    }

    @RequestMapping(value = "{orderId}", method = RequestMethod.GET)
    BaseResponse<?> getOrderDetail(@PathVariable(required = true) Long orderId){
        GetOrdersRes res = orderService.findOrderById(orderId);
        return new BaseResponse<>(res);
    }

    @RequestMapping(value = "{orderId}", method = RequestMethod.DELETE)
    BaseResponse<?> deleteOrder(@PathVariable(required = true) Long orderId){
        if (orderService.deleteOrder(orderId))
            return new BaseResponse<>(true, "Order cancel success", 200, null);
        else
            return new BaseResponse<>(BaseResponseStatus.RESPONSE_ERROR);
    }

}
