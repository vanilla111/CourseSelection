package com.database.cs.dao;

import com.database.cs.entity.CSelection;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CourseSelectDao {

    @Insert("insert into course_select (course_code, jxb_id, stu_id, type, created_at, updated_at) values" +
            "(#{courseCode},#{jxbId},#{stuId},#{type},#{createdAt},#{updatedAt})")
    boolean save(CSelection cs);

    @Select("select * from course_select where stu_id = #{sid}")
    List<CSelection> findByStuId(@Param("sid") String stuId);

    @Select("select * from course_select where stu_id = #{sid} and course_code = #{cc} limit 1")
    CSelection getOneByStuIdAndCourseCode(@Param("sid") String stuId, @Param("cc") String courseCode);
}
