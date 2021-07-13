package org.gonnaup.examples.springs.service.iml;

import org.gonnaup.examples.springs.beans.Subject;
import org.gonnaup.examples.springs.service.CashAccountService;
import org.gonnaup.examples.springs.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gonnaup
 * @version 2021/7/12 15:40
 */
@Service
public class SimpleSubjectService implements SubjectService {

    private final Map<Integer, Subject> subjectMap;

    {
        subjectMap = new HashMap<>();
        Subject buyer = new Subject();
        buyer.setId(100);
        buyer.setCashAccountId(CashAccountService.BUYER_ID);
        buyer.setName("张三");
        buyer.setNickName("Mr.Z");
        buyer.setPhone("18833339999");
        buyer.setAddress("中国");
        Subject seller = new Subject();
        seller.setId(200);
        seller.setCashAccountId(CashAccountService.SELLER_ID);
        seller.setName("李四");
        seller.setNickName("Mr.L");
        seller.setPhone("13588889999");
        seller.setAddress("中国");
        subjectMap.put(buyer.getId(), buyer);
        subjectMap.put(seller.getId(), seller);
    }

    @Override
    public Subject findSubjectById(Integer id) {
        return subjectMap.get(id);
    }
}
