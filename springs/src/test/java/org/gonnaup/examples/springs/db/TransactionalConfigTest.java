package org.gonnaup.examples.springs.db;

import lombok.extern.slf4j.Slf4j;
import org.gonnaup.examples.springs.beans.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author gonnaup
 * @version created at 2021/8/16 17:13
 */
@Slf4j
@SpringJUnitConfig(TransactionalConfig.class)
class TransactionalConfigTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testTransaction() {
        Product product = new Product();
        product.setId(88888888);
        product.setName("computer");
        product.setPrice(4799D);
        product.setOrigin("北京");
        product.setTotal(50);
        product.setDescription("联想笔记本");
        productRepository.insertProduct(product);
        product.setDescription("联想电脑");
        productRepository.updateProduct(product);
        Product db = productRepository.queryById(product.getId());
        log.info("查询数据 {}", db);
        productRepository.deleteProduct(product.getId());
        assertNull(productRepository.queryById(product.getId()));
    }

}