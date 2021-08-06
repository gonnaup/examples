package org.gonnaup.examples.springs.core.base;

import lombok.extern.slf4j.Slf4j;
import org.gonnaup.examples.springs.beans.Order;
import org.gonnaup.examples.springs.beans.Product;
import org.gonnaup.examples.springs.service.OrderService;
import org.gonnaup.examples.springs.service.ProductService;
import org.gonnaup.examples.springs.service.SubjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Slf4j
@SpringJUnitConfig(AnnotationBasedConfig.class)
class SpringBaseApplicationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SubjectService subjectService;

    @Test
    void contextLoads() {
        Product product = productService.findProductById(ProductService.PRODUCT_ID);
        log.info(product.toString());
        Order order = orderService.createOrder(product, 1, subjectService.findSubjectById(SubjectService.BUYER_ID));
        orderService.confirmOrder(order.getId());
        orderService.payOrder(order.getId(), subjectService.findSubjectById(SubjectService.BUYER_ID).getCashAccountId());
        orderService.sendOrder(order.getId());
        orderService.receiveOrder(order.getId(), SubjectService.BUYER_ID);
    }

}
