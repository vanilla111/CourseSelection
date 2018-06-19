package com.database.cs.dao;

import com.database.cs.entity.JxbOperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface JxbOperateDao {

    @Insert("insert into jxb_operate_log (who, desc, ip, created_at) values (#{who}, #{desc}, #{ip}, #{createdAt})")
    boolean save(JxbOperateLog jxbOperateLog);
}
