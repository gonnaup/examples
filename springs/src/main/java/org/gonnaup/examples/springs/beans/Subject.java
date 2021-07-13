package org.gonnaup.examples.springs.beans;

import lombok.Data;

/**
 * 用户主体
 *
 * @author gonnaup
 * @version 2021/7/12 11:06
 */
@Data
public class Subject {
    private Integer id;

    private String cashAccountId;

    private String name;

    private String nickName;

    private String phone;

    private String address;

}
