package com.example.demo.src.order;

import com.example.demo.src.order.model.Address;
import com.example.demo.src.order.model.GetOrdersRes;
import com.example.demo.src.order.model.PostOrderReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class OrderDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long createOrder(PostOrderReq req) {
        String creqteQuery = "insert into Orders(customer, seller, address, item, price) " +
                "values(?, ?, ?, ?, ?)";
        Long addressId = createAddress(req.getReceiverName(), req.getAddress(), req.getReceiverPhoneNumber());
        Object[] params = new Object[]{
                req.getCustomerId(), req.getSellerId(),
                addressId, req.getItemId(), req.getPrice()};
        jdbcTemplate.update(creqteQuery, params);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class);
    }

    public Address findAddressById(Long addressId){
        String findQuery = "Select * from Address where addressId = ?";
        Object[] params = new Object[]{addressId};
        return jdbcTemplate.queryForObject(findQuery,
                (rs, rowNum)->new Address(
                        rs.getLong("addressId"),
                        rs.getString("receiverName"),
                        rs.getString("address"),
                        rs.getString("receiverPhoneNumber")
                ),params);
    }

    public Long createAddress(String receiverName, String address, String phone){
        String createQuery = "Insert into Address(receiverName, address, receiverPhoneNumber) values (?, ?, ?)";
        Object[] params = new Object[]{receiverName, address, phone};
        jdbcTemplate.update(createQuery, params);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class);
    }

    public boolean deleteOrder(Long orderId) {
        String deleteQuery = "update Orders set status = ? where orderId = ?";
        Object[] params = new Object[]{"d", orderId};
        return jdbcTemplate.update(deleteQuery, params) == 1;
    }

    public GetOrdersRes findOrderById(Long orderId) {
        String getQuery = "select *, (select name from Item where Item.itemId = Orders.item) as itemName, " +
                "(select address from  Address where Address.addressId = Orders.address) as addressString"+
                " from Orders where orderId = ?";
        Object[] params = new Object[]{orderId};
        return jdbcTemplate.queryForObject(getQuery,
                (rs, rowNum) -> new GetOrdersRes(
                        rs.getLong("orderId"),
                        rs.getTime("updatedAt"),
                        rs.getString("itemName"),
                        rs.getLong("item"),
                        null,
                        rs.getInt("price"),
                        rs.getString("status")
                ), params);

    }
}
