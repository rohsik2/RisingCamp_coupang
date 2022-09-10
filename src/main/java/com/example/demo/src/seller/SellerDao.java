package com.example.demo.src.seller;

import com.example.demo.src.seller.model.Seller;
import com.example.demo.src.seller.model.dto.PostSellerReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class SellerDao {


    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // ******************************************************************************

    public Long save(PostSellerReq req){
        String createSellerQuery = "insert into Seller(name, introduce) values(?, ?)";
        Object[] params = new Object[]{req.getName(), req.getIntroduce()};
        this.jdbcTemplate.update(createSellerQuery, params);
        String lastInserIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, Long.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환한다.
    }

    public Seller findById(Long sellerId) {
        String q = "select * from Seller where sellerId = ?";
        Object[] params = new Object[]{sellerId};
        return this.jdbcTemplate.queryForObject(q,
                (rs, rowNum) -> new Seller(
                        rs.getLong("sellerId"),
                        rs.getString("name"),
                        rs.getTime("updatedAt"),
                        rs.getTime("createdAt"),
                        rs.getString("status"),
                        rs.getString("introduce")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                params);
    }

    public boolean delete(Long sellerId) {
        String q = "delete from Seller where sellerId = ?";
        Object[] params = new Object[]{sellerId};
        return this.jdbcTemplate.update(q, params) == 1;
    }

    public boolean isSellerIdExist(Long sellerId) {
        String q = "select count(*) from Seller where sellerId = ?";
        Object[] params = new Object[]{sellerId};
        return this.jdbcTemplate.queryForObject(q, Integer.class, params) > 0;

    }
}
