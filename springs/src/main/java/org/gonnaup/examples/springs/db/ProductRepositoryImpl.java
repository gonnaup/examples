package org.gonnaup.examples.springs.db;

import lombok.extern.slf4j.Slf4j;
import org.gonnaup.examples.springs.beans.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author gonnaup
 * @version created at 2021/8/16 16:55
 */
@Slf4j
@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Product queryById(Integer id) {
        List<Product> productList = jdbcTemplate.query("select name, price, origin, total, description from product where id = ?", (rs, rowNum) -> {
            Product product = new Product();
            product.setId(id);
            product.setName(rs.getString(1));
            product.setPrice(rs.getDouble(2));
            product.setOrigin(rs.getString(3));
            product.setTotal(rs.getDouble(4));
            product.setDescription(rs.getString(5));
            return product;
        }, id);
        return productList.isEmpty() ? null : productList.get(0);
    }

    @Override
    public int insertProduct(Product product) {
        return jdbcTemplate.update("insert into product(id, name, price, origin, total, description) values(?, ?, ?, ?, ?, ?)"
                , product.getId(), product.getName(), product.getPrice(), product.getOrigin(), product.getTotal(), product.getDescription());
    }

    @Override
    public int updateProduct(Product product) {
        return jdbcTemplate.update("update product set price = ?, origin = ?, total = ?, description = ? where id = ?"
                , product.getPrice(), product.getOrigin(), product.getTotal(), product.getDescription(), product.getId());
    }

    @Override
    public int deleteProduct(Integer id) {
        return jdbcTemplate.update("delete from product where id = ?", id);
    }
}
