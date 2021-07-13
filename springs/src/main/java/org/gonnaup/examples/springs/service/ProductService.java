package org.gonnaup.examples.springs.service;

import org.gonnaup.examples.springs.beans.Product;

/**
 * @author gonnaup
 * @version 2021/7/12 15:53
 */
public interface ProductService {

    Integer PRODUCT_ID = 100;

    Product findProductById(Integer id);

    /**
     * @param id
     * @param change
     * @return
     */
    boolean updateTotal(Integer id, double change);

}
