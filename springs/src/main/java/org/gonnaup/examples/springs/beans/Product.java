package org.gonnaup.examples.springs.beans;

import lombok.Data;

/**
 * 产品对象
 *
 * @author gonnaup
 * @version 2021/7/12 10:21
 */
@Data
public class Product {

    private Integer id;

    private String name;

    private Double price;

    //产地
    private String origin;

    private String description;

    //总量
    private double total;
}
