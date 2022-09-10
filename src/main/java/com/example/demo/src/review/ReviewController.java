package com.example.demo.src.review;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.item.ItemService;
import com.example.demo.src.review.model.PostReviewReq;
import com.example.demo.src.review.model.PostReviewRes;
import com.example.demo.src.customer.CustomerService;
import com.example.demo.src.review.model.Review;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/reviews") @RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ItemService itemService;
    private final CustomerService customerService;
    private final JwtService jwtService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public BaseResponse<?> getReviews(@RequestParam(required = false) Long itemId, @RequestParam(required = false) Long userId){
        if((itemId == null) == (userId == null)){
            return new BaseResponse<>(BaseResponseStatus.REVIEW_SEARCH_ARGS_FAIL);
        }
        if(itemId != null){
            if(!itemService.isItemIdExist(itemId)){
                return new BaseResponse<>(BaseResponseStatus.ITEM_NOT_FOUND);
            }
            return new BaseResponse<>(reviewService.getReviewsByItemId(itemId));
        }

        if(!customerService.isCustomerIdExist(userId)){
            return new BaseResponse<>(BaseResponseStatus.USER_NOT_FOUND);
        }
        return new BaseResponse<>(reviewService.getReviewsByUserId(userId));
    }

    @PostMapping(value = "")
    public BaseResponse<?> postItemReview(PostReviewReq req){
        try{
            PostReviewRes res = reviewService.postReview(req);
            return new BaseResponse<>(res);
        }
        catch(Exception e){
            return new BaseResponse<>(BaseResponseStatus.RESPONSE_ERROR);
        }
    }


    @DeleteMapping(value = "/{reviewId}")
    public BaseResponse<?> deleteReview(@PathVariable Long reviewId){
        if(!reviewService.isReviewExist(reviewId)){
            return new BaseResponse<>(BaseResponseStatus.REVIEW_NOT_FOUND);
        }
        Review order = reviewService.getReviewByReviewId(reviewId);
        try{
            if(jwtService.getUserIdx() != order.getCustomerId()){
                return new BaseResponse(BaseResponseStatus.REQUEST_ERROR);
            }
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        boolean result = reviewService.deleteByReviewId(reviewId);
        if(result)
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        else
            return new BaseResponse<>(BaseResponseStatus.DATABASE_ERROR);
    }

}
