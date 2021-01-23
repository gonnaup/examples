package org.gonnaup.examples.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author gonnaup
 * @version 2021/1/21 21:11
 */
@Data
public class User {

    private Long id;

    private String name;

    private int age;

    private String email;
}
