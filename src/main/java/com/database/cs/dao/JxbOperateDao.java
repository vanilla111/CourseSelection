package com.database.cs.dao;

import com.database.cs.entity.JxbOperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface JxbOperateDao {

    @Insert("insert into jxb_operate_log (admin_id, text, ip, created_at) values (#{adminId}, #{text}, #{ip}, #{createdAt})")
    boolean save(JxbOperateLog jxbOperateLog);
}
