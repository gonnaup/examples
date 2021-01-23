package org.gonnaup.examples.mybatisplus;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.gonnaup.examples.mybatisplus.dao.UserDao;
import org.gonnaup.examples.mybatisplus.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author gonnaup
 * @version 2021/1/21 21:31
 */
@SpringBootTest
public class UserDaoTest {

    @Autowired
    UserDao userDao;

    @Test
    void userTest() {
        userDao.selectList(null).forEach(System.out::println);
        System.out.println("------------------------------------------------------");
        userDao.selectList(Wrappers.lambdaQuery(User.class).likeRight(User::getName, "J")).forEach(System.out::println);
        System.out.println("------------------------------------------------------");
        Page<User> j = userDao.selectPage(new Page<User>(1, 1), Wrappers.lambdaQuery(User.class).likeRight(User::getName, "J"));
        System.out.println(j.getRecords());
    }
}
