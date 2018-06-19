package com.database.cs.dao;

import com.database.cs.entity.TAcademy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TAcademyDao {

    @Select("select * from t_academy where aid=#{aid}")
    TAcademy getOne(@Param("aid") String aid);
}
