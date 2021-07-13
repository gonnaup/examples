package org.gonnaup.examples.springs.enums;

/**
 * @author gonnaup
 * @version 2021/7/12 11:25
 */
public enum OrderStatus {
    CT("created"),
    CF("confirmed"),
    PD("paied"),
    SD("sended"),//已发货
    RV("received"),
    EX("exception"),
    CL("canceled");
    private final String description;

    private OrderStatus(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
