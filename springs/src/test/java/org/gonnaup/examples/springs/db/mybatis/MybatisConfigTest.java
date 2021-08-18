package org.gonnaup.examples.springs.db.mybatis;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author gonnaup
 * @version created at 2021/8/18 14:48
 */
@SpringJUnitConfig(MybatisConfig.class)
class MybatisConfigTest {

    @Autowired
    TeacherDao teacherDao;

    @Test
    void testMybatis() {
        Teacher teacher = new Teacher();
        teacher.setId(RandomUtils.nextInt(1, 100000));
        teacher.setName(RandomStringUtils.randomAlphabetic(5, 20));
        teacher.setAge(RandomUtils.nextInt(20, 50));
        teacher.setLevel(RandomStringUtils.randomNumeric(1));
        teacher.setSchool(RandomStringUtils.randomAlphabetic(10, 20));
        teacherDao.addTeacher(teacher);
        assertEquals(teacherDao.findOne(teacher.getId()), teacher);
        teacher.setSchool("XXX");
        teacherDao.updateTeacher(teacher);
        assertEquals(teacherDao.findOne(teacher.getId()), teacher);
        teacherDao.deleteTeacher(teacher.getId());
        assertNull(teacherDao.findOne(teacher.getId()));
    }

}