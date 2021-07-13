package org.gonnaup.examples.springs.service;

import org.gonnaup.examples.springs.beans.CashAccount;

/**
 * @author gonnaup
 * @version 2021/7/12 16:20
 */
public interface CashAccountService {

    String BUYER_ID = "111111111111";

    String SELLER_ID = "222222222222";

    CashAccount findCashAccountById(String id);

    boolean updateCashAccount(String id, double change);

}
