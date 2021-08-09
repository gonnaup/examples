package org.gonnaup.examples.springs.core.config;

import lombok.extern.slf4j.Slf4j;
import org.gonnaup.examples.springs.beans.Order;
import org.gonnaup.examples.springs.beans.Product;
import org.gonnaup.examples.springs.service.OrderService;
import org.gonnaup.examples.springs.service.ProductService;
import org.gonnaup.examples.springs.service.SubjectService;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @author gonnaup
 * @version created at 2021/8/9 12:57
 */
@Slf4j
@SpringJUnitConfig(TimeSpentAspectConfig.class)
public class TimeSpentAspectConfigTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SubjectService subjectService;

    @RepeatedTest(3)
    void testTimeSpentAspectConfig() {
        Product product = productService.findProductById(ProductService.PRODUCT_ID);
        log.info(product.toString());
        Order order = orderService.createOrder(product, 1, subjectService.findSubjectById(SubjectService.BUYER_ID));
        orderService.confirmOrder(order.getId());
        orderService.payOrder(order.getId(), subjectService.findSubjectById(SubjectService.BUYER_ID).getCashAccountId());
        orderService.sendOrder(order.getId());
        orderService.receiveOrder(order.getId(), SubjectService.BUYER_ID);
    }

}