package org.gonnaup.examples.springs.db.mybatis;

import org.springframework.stereotype.Repository;

/**
 * teacher DAO
 *
 * @author gonnaup
 * @version created at 2021/8/18 14:28
 */
@Repository
public interface TeacherDao {

    Teacher findOne(Integer id);

    void addTeacher(Teacher teacher);

    int updateTeacher(Teacher teacher);

    int deleteTeacher(Integer id);

}
