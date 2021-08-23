package org.gonnaup.examples.springs.mvc.xml;

import org.gonnaup.examples.springs.db.hibernate.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author gonnaup
 * @version created at 2021/8/20 21:19
 */
@Controller
public class StudentControler {


    @GetMapping("/student/list")
    public String listOne(Model model) {
        Student student = new Student();
        student.setId(10000);
        student.setName("张三");
        student.setAge(25);
        student.setCourse("计算机");
        student.setScore(99);
        model.addAttribute("student", student);
        return "student";
    }

    @GetMapping("/printname")
    @ResponseBody
    public String printname(@RequestParam("name") String name) {
        return name;
    }

}
