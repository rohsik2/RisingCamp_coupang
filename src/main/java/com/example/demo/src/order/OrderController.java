package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.item.ItemService;
import com.example.demo.src.order.model.GetOrdersRes;
import com.example.demo.src.order.model.PatchOrderReq;
import com.example.demo.src.order.model.PostOrderReq;
import com.example.demo.src.seller.SellerService;
import com.example.demo.src.customer.CustomerService;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/orders") @RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ItemService itemService;
    private final CustomerService userService;
    private final SellerService sellerService;
    private final JwtService jwtService;
    @RequestMapping(value = "", method= RequestMethod.POST)
    BaseResponse<?> newOrder(PostOrderReq req){
        try{
            if(jwtService.getUserIdx() != req.getCustomerId()){
                return new BaseResponse(BaseResponseStatus.REQUEST_ERROR);
            }
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        if(!itemService.isItemIdExist(req.getItemId())){
            return new BaseResponse<>(BaseResponseStatus.ITEM_NOT_FOUND);
        }
        return new BaseResponse (orderService.createOrder(req));
    }

    @RequestMapping(value = "{orderId}", method = RequestMethod.GET)
    BaseResponse<?> getOrderDetail(@PathVariable(required = true) Long orderId){
        GetOrdersRes res = orderService.findOrderById(orderId);
        try{
            if(jwtService.getUserIdx() != res.getCustomerId()){
                return new BaseResponse(BaseResponseStatus.REQUEST_ERROR);
            }
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
        return new BaseResponse<>(res);
    }

    @RequestMapping(value = "{orderId}", method = RequestMethod.DELETE)
    BaseResponse<?> deleteOrder(@PathVariable(required = true) Long orderId){
        GetOrdersRes order = orderService.findOrderById(orderId);
        try{
            if(jwtService.getUserIdx() != order.getCustomerId()){
                return new BaseResponse(BaseResponseStatus.REQUEST_ERROR);
            }
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
        if (orderService.deleteOrder(orderId))
            return new BaseResponse<>(true, "Order cancel success", 200, null);
        else
            return new BaseResponse<>(BaseResponseStatus.RESPONSE_ERROR);
    }

    @RequestMapping(value="/search", method=RequestMethod.GET)
    BaseResponse<?> searchOrders(@RequestParam(required = false) Long itemId,
                                 @RequestParam(required = false) Long userId,
                                 @RequestParam(required = false) Long sellerId){
        if(itemId != null && userId == null && sellerId == null){
            if(!itemService.isItemIdExist(itemId))
                return new BaseResponse<>(BaseResponseStatus.ITEM_NOT_FOUND);
            return new BaseResponse<>(orderService.searchOrdersByItemId(itemId));

        }
        else if(itemId == null && userId != null && sellerId == null){
            if(!userService.isCustomerIdExist(userId))
                return new BaseResponse<>(BaseResponseStatus.USER_NOT_FOUND);
            try{
                if(userId == jwtService.getUserIdx())
                    return new BaseResponse<>(orderService.searchOrdersByCustomerId(userId));

            } catch (BaseException e) {
                return new BaseResponse<>(e.getStatus());
            }

        }
        else if(itemId == null && userId == null && sellerId != null){
            if(!sellerService.isSellerIdExist(sellerId))
                return new BaseResponse<>(BaseResponseStatus.SELLER_NOT_FOUND);
            return new BaseResponse<>(orderService.searchOrdersBySellerId(sellerId));
        }
        else{
            return new BaseResponse<>(BaseResponseStatus.ORDER_SEARCH_PARAM_ERROR);
        }
        return new BaseResponse<>(orderService.searchOrdersByCustomerId(userId));
    }


    @PatchMapping(value = "{orderId}")
    BaseResponse<?> orderCancel(@PathVariable Long orderId, PatchOrderReq req){
        GetOrdersRes order = orderService.findOrderById(orderId);
        try{
            if(jwtService.getUserIdx() != order.getCustomerId()){
                return new BaseResponse(BaseResponseStatus.REQUEST_ERROR);
            }
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
        if(orderService.isOrderExist(orderId)){
            if(orderService.patchOrderStatus(req))
                return new BaseResponse<>(BaseResponseStatus.SUCCESS);
            else
                return new BaseResponse<>(BaseResponseStatus.ORDER_STATUS_PARAM_ERROR);
        }
        return new BaseResponse<>(BaseResponseStatus.ORDER_NOT_FOUND);
    }
}
