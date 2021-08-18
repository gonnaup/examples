package org.gonnaup.examples.springs.db.mybatis;

import lombok.Data;

/**
 * mybatis实体类
 * @author gonnaup
 * @version created at 2021/8/18 13:38
 */
@Data
public class Teacher {

    private Integer id;

    private String name;

    private int age;

    private String school;

    private  String level;

}
