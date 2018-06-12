package com.database.cs.dao;

import com.database.cs.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface StudentDao {

    @Select("select * from student where stu_id=#{stu_id}")
    public Student getByStuId(@Param("stu_id") String stuId);
}
