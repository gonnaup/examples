package org.gonnaup.examples.springs.service;

import org.gonnaup.examples.springs.beans.Subject;

/**
 * @author gonnaup
 * @version 2021/7/12 11:22
 */
public interface SubjectService {

    Integer BUYER_ID = 100;

    Integer SELLER_ID = 200;

    Subject findSubjectById(Integer id);

}
