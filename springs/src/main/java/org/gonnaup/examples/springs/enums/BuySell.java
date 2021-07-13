package org.gonnaup.examples.springs.enums;

/**
 * 买卖方向
 *
 * @author gonnaup
 * @version 2021/7/12 10:31
 */
public enum BuySell {
    B("buy"), S("sell");
    private final String description;

    private BuySell(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
