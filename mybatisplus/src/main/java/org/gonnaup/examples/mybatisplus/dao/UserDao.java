package org.gonnaup.examples.mybatisplus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.gonnaup.examples.mybatisplus.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author gonnaup
 * @version 2021/1/21 21:15
 */
@Repository
public interface UserDao extends BaseMapper<User> {
}
