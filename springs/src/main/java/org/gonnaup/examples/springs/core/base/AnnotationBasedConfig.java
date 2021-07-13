package org.gonnaup.examples.springs.core.base;

import lombok.extern.slf4j.Slf4j;
import org.gonnaup.examples.springs.beans.Order;
import org.gonnaup.examples.springs.beans.Product;
import org.gonnaup.examples.springs.service.OrderService;
import org.gonnaup.examples.springs.service.ProductService;
import org.gonnaup.examples.springs.service.SubjectService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author gonnaup
 * @version 2021/7/13 16:31
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = "org.gonnaup.examples.springs.service")
public class AnnotationBasedConfig {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AnnotationBasedConfig.class);
        ProductService productService = applicationContext.getBean(ProductService.class);
        OrderService orderService = applicationContext.getBean(OrderService.class);
        SubjectService subjectService = applicationContext.getBean(SubjectService.class);
        Product product = productService.findProductById(ProductService.PRODUCT_ID);
        log.info(product.toString());
        Order order = orderService.createOrder(product, 1, subjectService.findSubjectById(SubjectService.BUYER_ID));
        orderService.confirmOrder(order.getId());
        orderService.payOrder(order.getId(), subjectService.findSubjectById(SubjectService.BUYER_ID).getCashAccountId());
        orderService.sendOrder(order.getId());
        orderService.receiveOrder(order.getId(), SubjectService.BUYER_ID);
    }

}
