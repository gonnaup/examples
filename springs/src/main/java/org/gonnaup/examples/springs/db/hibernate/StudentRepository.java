package org.gonnaup.examples.springs.db.hibernate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;

/**
 * @author gonnaup
 * @version created at 2021/8/17 16:19
 */
@Slf4j
@Repository
@Transactional
public class StudentRepository {

    private final HibernateTemplate hibernateTemplate;

    public StudentRepository(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public Student findOne(Integer id) {
        return hibernateTemplate.get(Student.class, id);
    }

    public Student addStudent(Student student) {
        Integer id = (Integer) hibernateTemplate.save(student);
        student.setId(id);
        log.info("添加 {} 成功", student);
        return student;
    }


    public void updateStudent(Student student) {
        hibernateTemplate.update(student);
        log.info("更新 {} 成功", student);
    }

    public void deleteStudent(Integer id) {
        Integer delNum = hibernateTemplate
                .execute(session -> {
                    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                    CriteriaDelete<Student> criteriaDelete = criteriaBuilder.createCriteriaDelete(Student.class);
                    Root<Student> root = criteriaDelete.from(Student.class);
                    return session.createQuery(criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id))).executeUpdate();
                });
        if (delNum != null && delNum > 0) {
            log.info("删除 id = [{}] 的Student成功！", id);
        }


//        Integer deleteNumber = hibernateTemplate
//                .executeWithNativeSession(session -> session.createNativeQuery("delete from Student where id = ?").setParameter(1, id).executeUpdate());
//        if (deleteNumber != null && deleteNumber > 0) {
//            log.info("删除 id = [{}] 的Student成功！", id);
//        }


//        Student obj = findOne(id);
//        if (obj != null) {
//            hibernateTemplate.delete(obj);
//            log.info("删除 {} 成功", id);
//        }
    }

}
