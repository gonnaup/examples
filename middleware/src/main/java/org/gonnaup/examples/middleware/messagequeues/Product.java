package org.gonnaup.examples.middleware.messagequeues;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 产品对象
 *
 * @author gonnaup
 * @version 2021/7/12 10:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Integer id;

    private String name;

    private Double price;

    //产地
    private String origin;

    //总量
    private double total;

    private String description;

    private LocalDate createDate;

    private LocalDateTime updateTime;
}
