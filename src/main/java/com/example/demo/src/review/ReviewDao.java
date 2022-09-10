package com.example.demo.src.review;


import com.example.demo.src.review.model.PostReviewReq;
import com.example.demo.src.review.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReviewDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // ******************************************************************************

    public List<Review> getReviewsByItemId(Long itemId) {
        String q = "select * from Review where itemId = ?";
        Object[] params = new Object[]{itemId};
        return jdbcTemplate.query(q, (rs, count) ->new Review(
                rs.getLong("reviewId"),
                rs.getString("text"),
                rs.getFloat("score"),
                rs.getLong("itemId"),
                rs.getLong("customerId")
        ), params);
    }

    public Long createReview(PostReviewReq req) {
        String createQuery = "Insert into Review(text, score, itemId, customerId)" +
                " values (?, ?, ?, ?)";
        Object[] params = new Object[]{req.getText(), req.getScore(), req.getItemId(), req.getUserId()};

        jdbcTemplate.update(createQuery, params);
        String lastInserIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, Long.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환한다.

    }

    public List<Review> getReviewsByUserId(Long customerId) {
        String q = "select * from Review where customerId = ?";
        Object[] params = new Object[]{customerId};
        return jdbcTemplate.query(q, (rs, count) ->new Review(
                rs.getLong("reviewId"),
                rs.getString("text"),
                rs.getFloat("score"),
                rs.getLong("itemId"),
                rs.getLong("customerId")
        ), params);
    }

    public boolean isReviewExist(Long reviewId) {
        String query = "select count(*) from Review where reviewId = ?";
        Object[] params = new Object[]{reviewId};
        return jdbcTemplate.queryForObject(query, Integer.class, params) > 0;
    }

    public Boolean deleteByReviewId(Long reviewId) {
        String q = "delete from Review where reviewId = ?";
        Object[] params = new Object[]{reviewId};
        return this.jdbcTemplate.update(q, params) == 1;
    }

    public Review getReviewByReviewId(Long reviewId) {
        String q = "select * from Review where reviewId = ?";
        Object[] params = new Object[]{reviewId};
        return jdbcTemplate.queryForObject(q, (rs, count) ->new Review(
                rs.getLong("reviewId"),
                rs.getString("text"),
                rs.getFloat("score"),
                rs.getLong("itemId"),
                rs.getLong("customerId")
        ), params);
    }
}
