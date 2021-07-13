package org.gonnaup.examples.springs.service.iml;

import lombok.extern.slf4j.Slf4j;
import org.gonnaup.examples.springs.beans.CashAccount;
import org.gonnaup.examples.springs.beans.Order;
import org.gonnaup.examples.springs.beans.Product;
import org.gonnaup.examples.springs.beans.Subject;
import org.gonnaup.examples.springs.enums.OrderStatus;
import org.gonnaup.examples.springs.enums.YesNo;
import org.gonnaup.examples.springs.service.CashAccountService;
import org.gonnaup.examples.springs.service.OrderService;
import org.gonnaup.examples.springs.service.ProductService;
import org.gonnaup.examples.springs.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单订单逻辑
 *
 * @author gonnaup
 * @version 2021/7/12 11:20
 */
@Slf4j
@Service
public class SimpleOrderService implements OrderService {

    private final Map<Long, Order> orderMap;

    {
        orderMap = new ConcurrentHashMap<>();
    }

    @Autowired
    private ProductService productService;

    @Resource
    private CashAccountService cashAccountService;

    @Autowired
    private SubjectService subjectService;

    @Override
    public Order findOrder(Long id) {
        return orderMap.get(id);
    }

    @Override
    public void addOrder(Order order) {
        orderMap.put(order.getId(), order);
    }

    @Override
    public void updateOrder(Order order) {
        orderMap.put(order.getId(), order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderMap.remove(id);
    }

    @Override
    public Order createOrder(Product product, double amount, Subject subject) {
        Order order = new Order();
        order.setId(System.currentTimeMillis());
        order.setBusinessesId(SubjectService.SELLER_ID);
        order.setProductId(product.getId());
        order.setProductName(product.getName());
        order.setPrice(product.getPrice());
        order.setQuantity(amount);
        order.setClearing(YesNo.N.name());
        order.setOrderStatus(OrderStatus.CT.name());
        order.setBuyerName(subject.getNickName());
        order.setBuyerPhone(subject.getPhone());
        order.setBuyerAddress(subject.getAddress());
        order.setCreateTime(LocalDateTime.now());
        order.setCreateTime(LocalDateTime.now());
        orderMap.put(order.getId(), order);
        log.info("创建订单 {} 成功!", order);
        return order;
    }

    @Override
    public boolean confirmOrder(Long id) {
        Order order = findOrder(id);
        if (order.getPrice() > 0 && productService.findProductById(order.getProductId()).getTotal() > order.getQuantity()) {
            log.info("订单确认，正常");
            order.setOrderStatus(OrderStatus.CF.name());
            updateOrder(order);
            return true;
        } else {
            log.error("订单价格或数量异常");
            order.setOrderStatus(OrderStatus.EX.name());
            updateOrder(order);
            return false;
        }
    }

    @Override
    @SuppressWarnings("all")
    public void payOrder(Long orderId, String cashId) {
        final Order order = findOrder(orderId);
        //模拟分布式锁

        synchronized (order) {
            CashAccount buyerCashAccount = cashAccountService.findCashAccountById(cashId);
            CashAccount sellerCashAccount = cashAccountService.findCashAccountById(subjectService.findSubjectById(order.getBusinessesId()).getCashAccountId());
            double cash = BigDecimal.valueOf(order.getPrice()).multiply(BigDecimal.valueOf(order.getQuantity())).setScale(2, RoundingMode.HALF_UP).doubleValue();
            cashAccountService.updateCashAccount(buyerCashAccount.getId(), -cash);
            cashAccountService.updateCashAccount(sellerCashAccount.getId(), cash);
            log.info("订单 {} 付款 {} 成功", orderId, cash);
        }
    }

    @Override
    public void sendOrder(Long orderId) {
        Order order = findOrder(orderId);
        order.setOrderStatus(OrderStatus.SD.name());
        updateOrder(order);
        log.info("订单 {} 已发货", orderId);
    }

    @Override
    public void receiveOrder(Long orderId, Integer subjectId) {
        Order order = findOrder(orderId);
        order.setOrderStatus(OrderStatus.RV.name());
        updateOrder(order);
        Subject subject = subjectService.findSubjectById(subjectId);
        log.info("订单 {} 已由 {}-{} 收货", orderId, subject.getNickName(), subject.getPhone());
    }
}
