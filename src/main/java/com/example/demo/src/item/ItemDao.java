package com.example.demo.src.item;

import com.example.demo.src.item.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public SearchItemRes searchItems(SearchItemReq req){
        String query = "select *,\n" +
                "       (select url from PhotoItem where PhotoItem.itemId = Item.itemId limit 1) as basePhotoUrl,\n" +
                "       (select count(*) from Review where Review.itemId = Item.itemId) as reviewNum,\n" +
                "       (select avg(score) from Review where Review.itemId = Item.itemId) as score\n" +
                "from Item where text like ? and category like ? order by ? desc;";
        Object[] params = new Object[]{"%"+req.getSearchKeyWord()+"%", "%"+req.getCategory()+"%", req.getOrder()};
        List<SearchItemRes.ItemThumbnail> searchItems = this.jdbcTemplate.query(query,
                (rs, rowNum) -> new SearchItemRes.ItemThumbnail(
                        rs.getLong("itemId"),
                        rs.getString("basePhotoUrl"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getDouble("score"),
                        rs.getInt("reviewNum")
                ), params);
        return new SearchItemRes(req.getSearchKeyWord(), req.getCategory(), searchItems);

    }

    public List<PhotoItem> searchPhotosByItemId(Long itemId){

        return new ArrayList<PhotoItem>();
    }

    public Long createItem(PostItemReq req){
        String newItemQuery = "INSERT INTO Item (itemId, name, price, quantity, sellerId, text, category, updatedAt, createdAt, status, isRocket) " +
                "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, DEFAULT, DEFAULT, DEFAULT, DEFAULT)";
        Object[] params = new Object[]{req.getName(), req.getPrice(), req.getQuantity(), req.getSellerId(), req.getText(), req.getCategory()};
        this.jdbcTemplate.update(newItemQuery, params);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class);

    }


    public GetItemRes getItem(Long itemId) {
        String getItemQuery = "select *, " +
                "(select name from Seller where Seller.sellerId = Item.sellerId) as sellerName " +
                "from Item where itemId = ?;"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        Object[] params = new Object[]{itemId};
        return this.jdbcTemplate.queryForObject(getItemQuery,
                (rs, rowNum) -> new GetItemRes(
                        rs.getLong("itemId"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("quantity"),
                        rs.getString("text"),
                        rs.getString("category"),
                        rs.getString("sellerName"),
                        rs.getLong("sellerId")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                params); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    public boolean deleteItem(Long itemId) {
        String deleteQuery = "Delete from Item where itemId = ?";
        Object[] params = new Object[]{itemId};
        return jdbcTemplate.update(deleteQuery, params) == 1;
    }
}
