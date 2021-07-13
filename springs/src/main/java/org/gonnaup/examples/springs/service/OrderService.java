package org.gonnaup.examples.springs.service;

import org.gonnaup.examples.springs.beans.Order;
import org.gonnaup.examples.springs.beans.Product;
import org.gonnaup.examples.springs.beans.Subject;

/**
 * 订单服务，只做简单模拟，与现实服务有别
 *
 * @author gonnaup
 * @version 2021/7/12 10:55
 */
public interface OrderService {

    Order findOrder(Long id);

    void addOrder(Order order);

    void updateOrder(Order order);

    void deleteOrder(Long id);

    Order createOrder(Product product, double amount, Subject subject);

    boolean confirmOrder(Long id);

    void payOrder(Long orderId, String cashId);

    void sendOrder(Long orderId);

    void receiveOrder(Long orderId, Integer subjectId);

}
