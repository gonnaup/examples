package org.gonnaup.examples.springs.service.iml;

import lombok.extern.slf4j.Slf4j;
import org.gonnaup.examples.springs.beans.CashAccount;
import org.gonnaup.examples.springs.service.CashAccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gonnaup
 * @version 2021/7/13 11:07
 */
@Slf4j
@Service
public class SimpleCashAccountService implements CashAccountService {

    private final Map<String, CashAccount> cashAccountMap;

    {
        cashAccountMap = new ConcurrentHashMap<>(2);
        CashAccount buyer = new CashAccount();
        buyer.setId(CashAccountService.BUYER_ID);
        buyer.setName("张三");
        buyer.setCash(20000);

        CashAccount seller = new CashAccount();
        seller.setId(CashAccountService.SELLER_ID);
        seller.setName("李四");
        seller.setCash(10000);

        cashAccountMap.put(buyer.getId(), buyer);
        cashAccountMap.put(seller.getId(), seller);
    }

    @Override
    public CashAccount findCashAccountById(String id) {
        return cashAccountMap.get(id);
    }

    @Override
    public synchronized boolean updateCashAccount(String id, double change) {
        CashAccount cashAccount = cashAccountMap.get(id);
        cashAccount.setCash(BigDecimal.valueOf(cashAccount.getCash()).add(BigDecimal.valueOf(change)).doubleValue());
        log.info("账号 {} 变化 {}", id, change);
        return true;
    }
}
