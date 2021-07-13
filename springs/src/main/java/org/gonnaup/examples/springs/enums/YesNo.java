package org.gonnaup.examples.springs.enums;

/**
 * @author gonnaup
 * @version 2021/7/12 11:01
 */
public enum YesNo {
    Y("yes"), N("no");
    private final String description;

    private YesNo(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
