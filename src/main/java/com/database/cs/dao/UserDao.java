package com.database.cs.dao;

import com.database.cs.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserDao {

    @Insert("insert into user (user_name, password, role) values (#{userName},#{password},#{role})")
    boolean save(User user);

    @Select("select * from user where user_name=#{name}")
    User getOne(@Param("name") String userName);

    @Update("update user set password=#{password} where user_name=#{userName}")
    boolean updateOne(User user);
}
