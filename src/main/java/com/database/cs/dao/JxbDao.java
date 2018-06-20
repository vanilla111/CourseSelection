package com.database.cs.dao;

import com.database.cs.entity.JXB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface JxbDao {

    void saveNewJxb(JXB jxb);

    List<JXB> findByJxbContaining(JXB jxb);

    void updateJxb(JXB jxb);

    List<JXB> findByJxbIdIn(List list);

    @Select("select * from jxb")
    List<JXB> getAll();

    @Select("select * from jxb where class_room=#{cr} and hash_day=#{hd} and year=#{year} and status=0")
    List<JXB> findByClassRoomAndHashDayAndYear(@Param("cr") String classRoom, @Param("hd") Integer hashDay, @Param("year") Integer year);

    @Select("select * from jxb where teacher_id=#{tid} and year=#{year}")
    List<JXB> findByTeaIdAndYear(@Param("tid") String teaId, @Param("year") int year);

    @Select("select * from jxb where course_code=#{cc} limit 1")
    JXB getOneJxbByCourseCode(@Param("cc") String courseCode);

    @Select("select * from jxb where course_code=#{cc} and status=0")
    List<JXB> findByCourseCode(@Param("cc") String courseCode);

    @Select("SELECT DISTINCT(course_code), jxb_name,day,lesson FROM jxb")
    List<JXB> findByDistinctCourseCode(@Param("cc") String courseCode);

    @Select("select * from jxb where jxb_id=#{jid} limit 1")
    JXB getOne(@Param("jid") String jxbId);
}
