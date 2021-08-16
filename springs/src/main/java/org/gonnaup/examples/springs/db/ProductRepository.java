package org.gonnaup.examples.springs.db;

import org.gonnaup.examples.springs.beans.Product;

/**
 * 产品数据访问接口
 * @author gonnaup
 * @version created at 2021/8/16 16:50
 */
public interface ProductRepository {

    Product queryById(Integer id);

    int insertProduct(Product product);

    int updateProduct(Product product);

    int deleteProduct(Integer id);

}
