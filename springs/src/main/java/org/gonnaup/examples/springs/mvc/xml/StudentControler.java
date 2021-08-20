package org.gonnaup.examples.springs.mvc.xml;

import org.gonnaup.examples.springs.db.hibernate.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gonnaup
 * @version created at 2021/8/20 21:19
 */
@RestController
public class StudentControler {


    @GetMapping("/student/list")
    public Student listOne() {
        Student student = new Student();
        student.setId(10000);
        student.setName("张三");
        student.setAge(25);
        student.setCourse("计算机");
        student.setScore(99);
        return student;
    }

}
