package org.gonnaup.examples.springs.service.iml;

import org.gonnaup.examples.springs.beans.Product;
import org.gonnaup.examples.springs.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gonnaup
 * @version 2021/7/12 15:56
 */
@Service
public class SimpleProductService implements ProductService {

    private final Map<Integer, Product> productMap;

    {
        productMap = new ConcurrentHashMap<>(1);
        Product product = new Product();
        product.setId(ProductService.PRODUCT_ID);
        product.setName("联想笔记本");
        product.setPrice(4299D);
        product.setOrigin("北京");
        product.setTotal(20);
        product.setDescription("AMD5500U + 16G + 512G");
        productMap.put(product.getId(), product);
    }

    @Override
    public Product findProductById(Integer id) {
        return productMap.get(id);
    }

    @Override
    public boolean updateTotal(Integer id, double change) {
        Product product = findProductById(id);
        product.setTotal(BigDecimal.valueOf(product.getTotal()).add(BigDecimal.valueOf(change)).doubleValue());
        return true;
    }
}
