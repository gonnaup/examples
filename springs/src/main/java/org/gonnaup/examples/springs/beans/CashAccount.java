package org.gonnaup.examples.springs.beans;

import lombok.Data;

/**
 * 资金账号
 *
 * @author gonnaup
 * @version 2021/7/12 11:04
 */
@Data
public class CashAccount {
    private String id;

    private String name;

    private double cash;
}
