package org.gonnaup.examples.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

/**
 * @author gonnaup
 * @version 2021/1/21 20:51
 */
@SpringBootApplication
@MapperScan(basePackages = "org.gonnaup.examples.mybatisplus.dao", annotationClass = Repository.class)
public class MybatisplusApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisplusApplication.class, args);
    }
}
