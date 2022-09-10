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
        String getItemQuery = "select * from Item\n" +
                "left outer join Seller " +
                "on Seller.sellerId = Item.sellerId " +
                "where Item.itemId = ?"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문

        Object[] params = new Object[]{itemId};
        GetItemRes res = this.jdbcTemplate.queryForObject(getItemQuery,
                (rs, rowNum) -> new GetItemRes(
                        rs.getLong("Item.itemId"),
                        rs.getString("Item.name"),
                        rs.getInt("Item.price"),
                        rs.getInt("Item.quantity"),
                        rs.getString("Item.text"),
                        rs.getString("Item.category"),
                        rs.getString("Seller.name"),
                        rs.getLong("Seller.sellerId")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                params);
        return res;
    }

    public boolean deleteItem(Long itemId) {
        String deleteQuery = "Delete from Item where itemId = ?";
        Object[] params = new Object[]{itemId};
        return jdbcTemplate.update(deleteQuery, params) == 1;
    }

    public List<Item> findItemsBySellerId(Long sellerId) {
        String query = "select * from Item where sellerId = ?";
        Object[] params = new Object[]{sellerId};
        List<Item> employees = jdbcTemplate.query(
                query, (rs, rowNum) -> new Item(
                        rs.getLong("itemId"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("quantity"),
                        rs.getLong("sellerId"),
                        rs.getString("text"),
                        rs.getString("category"),
                        rs.getDate("createdAt"),
                        rs.getDate("updatedAt"),
                        rs.getString("status"),
                        rs.getBoolean("isRocket"))
        , params);
        return employees;
    }

    public boolean deleteSellerId(long itemId) {
        String query = "update Item set sellerId = null where itemId = ?";
        Object[] params = new Object[]{itemId};
        return jdbcTemplate.update(query, params) == 1;
    }

    public Boolean isItemIdExist(Long itemId){
        String query = "select count(*) from Item where itemId = ?";
        Object[] params = new Object[]{itemId};
        return jdbcTemplate.queryForObject(query, Integer.class, params) > 0;
    }
}
