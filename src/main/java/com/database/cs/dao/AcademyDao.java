package com.database.cs.dao;

import com.database.cs.entity.Academy;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AcademyDao {

    @Insert("insert into academy (acad_name) values (#{acadName})")
    void save(Academy academy);

    @Select("select * from academy")
    List<Academy> getAll();

    @Select("select * from academy where acad_name like '%' #{name} '%'")
    List<Academy> findByName(@Param("name") String name);

    @Delete("delete from academy where id=#{id}")
    void deleteById(@Param("id") int id);
}
