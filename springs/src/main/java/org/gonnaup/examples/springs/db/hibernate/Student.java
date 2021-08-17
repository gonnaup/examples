package org.gonnaup.examples.springs.db.hibernate;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author gonnaup
 * @version created at 2021/8/17 16:13
 */
@Data
@Entity
public class Student implements Serializable {

    private static final long serialVersionUID = -5429429934754454863L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column
    private int age;

    @Column(length = 30)
    private String course;

    @Column
    private int score;

}
