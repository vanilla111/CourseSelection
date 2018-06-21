package com.database.cs.dao;

import com.database.cs.entity.CSelection;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CourseSelectDao {

    @Insert("insert into course_select (course_code, jxb_id, stu_id, type, created_at, updated_at, year, status) values" +
            "(#{courseCode},#{jxbId},#{stuId},#{type},#{createdAt},#{updatedAt},#{year},#{status})")
    @Options(useGeneratedKeys = true)
    void save(CSelection cs);

    @Select("select * from course_select where stu_id = #{sid} and status=1")
    List<CSelection> findByStuId(@Param("sid") String stuId);

    @Select("select * from course_select where jxb_id = #{jid} and status=1 order by stu_id asc")
    List<CSelection> findByJxbId(@Param("jid") String jxbId);

    @Select("select * from course_select where stu_id = #{sid} and course_code = #{cc} and status=1 limit 1")
    CSelection getOneByStuIdAndCourseCode(@Param("sid") String stuId, @Param("cc") String courseCode);

    @Select("select * from course_select where stu_id = #{sid} and jxb_id = #{jid} and status=1")
    CSelection getOneByStuIdAndJxbId(@Param("sid") String stuId, @Param("jid") String jxbId);

    @Update("update course_select set status=0 where stu_id = #{sid} and jxb_id = #{jid}")
    void cancelSelect(@Param("sid") String id, @Param("jid") String jxbId);
}
