package com.database.cs.dao;

import com.database.cs.entity.CMapping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CMappingDao {

    @Select("select * from course_mapping where course_code=#{code}")
    public List<CMapping> getByCourseCode(@Param("code") String code);
}
