package com.database.cs.dao;

import com.database.cs.entity.Teacher;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TeacherDao {

    @Select("select * from teacher where tea_id=#{tid} and status=1")
    Teacher getOne(@Param("tid") String teacherId);

    @Select("select aid,major,name,position,tea_id from teacher where name like '%' #{key} '%' or tea_id like '%' #{key} '%' and status=1")
    List<Teacher> findByTeaIdContainingOrNameContaining(@Param("key") String key);

    @Select("select aid,major,name,position,tea_id from teacher where status=1")
    List<Teacher> getAll();

    @Insert("insert into teacher (aid,major,name,password,position,tea_id) values (#{aid},#{major},#{name},#{password},#{position},#{teaId})")
    boolean save(Teacher teacher);

    @Update("update teacher set aid=#{aid},major=#{major},name=#{name},password=#{password},position=#{position} where tea_id=#{teaId}")
    boolean update(Teacher teacher);

    @Update("update teacher set status=0 where tea_id=#{teaId}")
    boolean delete(@Param("teaId") String teaId);
}
