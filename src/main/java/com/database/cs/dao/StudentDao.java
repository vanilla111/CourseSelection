package com.database.cs.dao;

import com.database.cs.entity.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface StudentDao {

    @Select("select * from student where stu_id=#{stu_id} and status=1")
    Student getOne(@Param("stu_id") String stuId);

    List<Student> findByStudentContaining(Student student);

    void updateOne(Student student);

    @Insert("insert into student (academy, major, class_num, gender, grade, name, stu_id, birthday) " +
            "values (#{academy},#{major},#{classNum},#{gender},#{grade},#{name},#{stuId},#{birthday})")
    boolean save(Student student);

    @Update("update student set status=0 where stu_id=#{stuId}")
    boolean delete(@Param("stuId") String stuId);
}
