package org.gonnaup.examples.springs.beans;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 订单对象
 *
 * @author gonnaup
 * @version 2021/7/12 10:24
 */
@Data
public class Order {

    private Long id;

    //商家id
    private Integer businessesId;

    private Integer productId;

    private String productName;

    private Double price;

    //数量
    private double quantity;

    //是否已清算
    private String clearing;

    //订单状态
    private String orderStatus;

    /*********** 客户信息 ***********/
    private String buyerName;

    private String buyerPhone;

    //运送地址
    private String buyerAddress;
    /******************************/

    private String detail;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

}
