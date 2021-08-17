package org.gonnaup.examples.springs.db.hibernate;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author gonnaup
 * @version created at 2021/8/17 16:33
 */
@Slf4j
@SpringJUnitConfig(HibernateConfig.class)
class HibernateConfigTest {

    @Autowired
    StudentRepository studentRepository;

    @Test
    void testHibernate() {
        Student student = new Student();
        student.setName(RandomStringUtils.randomAscii(RandomUtils.nextInt(10, 30)));
        student.setAge(RandomUtils.nextInt(16, 20));
        student.setCourse("计算机");
        student.setScore(RandomUtils.nextInt(82, 98));
        Student saved = studentRepository.addStudent(student);
        Student stu_db = studentRepository.findOne(saved.getId());
        assertEquals(stu_db, saved);
        stu_db.setName("UPDATE");
        studentRepository.updateStudent(stu_db);
        assertEquals(stu_db, studentRepository.findOne(stu_db.getId()));
        studentRepository.deleteStudent(stu_db.getId());
        assertNull(studentRepository.findOne(stu_db.getId()));
    }

}