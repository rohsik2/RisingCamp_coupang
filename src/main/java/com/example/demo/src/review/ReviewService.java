package com.example.demo.src.review;

import com.example.demo.src.review.model.GetReviewRes;
import com.example.demo.src.review.model.PostReviewReq;
import com.example.demo.src.review.model.PostReviewRes;
import com.example.demo.src.review.model.Review;
import com.example.demo.src.seller.model.dto.PostSellerRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class ReviewService {
    private final ReviewDao reviewDao;
    public GetReviewRes getReviewsByItemId(Long itemId) {
        List<Review> reviewList = reviewDao.getReviewsByItemId(itemId);
        return new GetReviewRes(reviewList);
    }

    public PostReviewRes postReview(PostReviewReq req) {
        Long reviewId = reviewDao.createReview(req);
        return new PostReviewRes(reviewId);
    }

    public GetReviewRes getReviewsByUserId(Long userId) {
        List<Review> reviewList = reviewDao.getReviewsByUserId(userId);
        return new GetReviewRes(reviewList);
    }

    public boolean isReviewExist(Long reviewId) {
        return reviewDao.isReviewExist(reviewId);
    }

    public Boolean deleteByReviewId(Long reviewId) {
        return reviewDao.deleteByReviewId(reviewId);
    }

    public Review getReviewByReviewId(Long reviewId){
        return reviewDao.getReviewByReviewId(reviewId);
    }
}
