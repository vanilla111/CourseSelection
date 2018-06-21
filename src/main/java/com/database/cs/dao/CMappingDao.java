package com.database.cs.dao;

import com.database.cs.entity.CMapping;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CMappingDao {

    @Select("select * from course_mapping where course_code=#{code}")
    List<CMapping> getByCourseCode(@Param("code") String code);

    @Select("select * from course_mapping where course_code=#{code} limit 1")
    CMapping getOne(@Param("code") String code);

    @Select("select * from course_mapping order by id desc")
    List<CMapping> getAll();

    @Select("select * from course_mapping where course_name=#{name}")
    List<CMapping> getByCourseName(@Param("name") String name);

    @Select("select * from course_mapping where course_code like '%' #{code} '%'")
    List<CMapping> findByCodeContaining(@Param("code") String code);

    @Select("select * from course_mapping where course_name like '%' #{name} '%'")
    List<CMapping> findByNameContaining(@Param("name") String name);

    @Select("select * from course_mapping where course_code like '%' #{str} '%' or course_name like '%' #{str} '%'")
    List<CMapping> findByCodeContainingOrNameContaining(@Param("str") String str);

    @Select("select * from course_mapping where course_code=#{code} or course_name=#{name}")
    List<CMapping> findByCodeAndName(@Param("code") String code, @Param("name") String name);

    @Insert("insert into course_mapping (course_code, course_name) values (#{cm.courseCode}, #{cm.courseName})")
    boolean save(@Param("cm") CMapping cMapping);

    @Delete("delete from course_mapping where course_code=#{cc}")
    boolean delete(@Param("cc") String courseCode);
}
